package com.rbondarovich.service.impl;

import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.exception.IncorrectInputData;
import com.rbondarovich.service.interfaces.Crawler;
import com.rbondarovich.service.interfaces.LinkService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Scans the Internet according to specified criteria.
 * To create LinkBean and WordCounterBean, save and get uses @see CrawlerServiceImpl
 * @author Roman Bondarovich
 */
@Service
@RequiredArgsConstructor
public class CrawlerImpl implements Crawler {

    /**
     * Used to create LinkBean and WordCounterBean, save and get data
     */
    private final CrawlerServiceImpl crawlerService;

    /**
     * Does @see searchMatches(String link, String[] terms, int limitDepth, int maxPages) if limitDepth and maxPages is not set.
     * The limitDepth = 8 and the maxPages = 10 000.
     * @param link search home page
     * @param terms search depth
     */
    @Override
    public void searchMatches(String link, String[] terms) {
        searchMatches(link, terms, 8);
    }

    /**
     * Does @see searchMatches(String link, String[] terms, int limitDepth, int maxPages) if limitDepth is not set.
     * The limitDepth = 8.
     * @param link search home page
     * @param maxPages words to find
     * @param terms search depth
     */
    @Override
    public void searchMatches(String link, int maxPages, String[] terms) {
        searchMatches(link, terms, 8, maxPages);
    }

    /**
     * Does @see searchMatches(String link, String[] terms, int limitDepth, int maxPages) if maxPages is not set.
     * The maxPages = 10 000.
     * @param link search home page
     * @param terms words to find
     * @param limitDepth search depth
     */
    @Override
    public void searchMatches(String link, String[] terms, int limitDepth) {
        searchMatches(link, terms, limitDepth, 10000);
    }

    /**
     * Searches for words and counts the number of matches on the page.
     * Starts scanning from the link page, visits the links on the page in depth @param limitDepth.
     * The search is performed in width. The maximum number of pages scanned is determined by @param maxPages
     * @param link scanned page
     * @param terms words to find
     * @param limitDepth search depth
     * @param maxPages maximum number of scanned pages
     */
    @Override
    public void searchMatches(String link, String[] terms, int limitDepth, int maxPages) throws IncorrectInputData{
        if(!crawlerService.validateURL(link)) {
            throw new IncorrectInputData("URL is bad");
        }
        String seed = convertLink(link);
        int currentDepth = 0;
        int currentPage;
        LinkBean seedLink = crawlerService.createLinkBean(link, currentDepth, null, seed);
        List<LinkBean> links = new ArrayList<>();
        links.add(seedLink);

        for (currentPage = 0; currentPage < links.size() && currentPage < maxPages; currentPage++) {
            LinkBean linkBean = links.get(currentPage);
            Document document = getDocument(linkBean);
            if (document != null) {
                crawlerService.saveLink(linkBean);
                LinkBean parentLink = crawlerService.getStoredLink(linkBean);

                if (parentLink.getLinkDepth() < limitDepth) {
                    links.addAll(findAllLinks(parentLink, document, seed));
                    removeDuplicates(links);
                }

                String text = getTextFromPage(document);
                for (String term : terms) {
                    int count = getNumberOfMatches(text, term);
                    crawlerService.saveTerm(crawlerService.createWordCounterBean(term, count, parentLink));
                }
            }
        }
    }

    private void removeDuplicates(List<LinkBean> list) {
        Set<LinkBean> set = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(set);
    }

    private Set<LinkBean> findAllLinks(LinkBean link, Document scannedDocument, String seed) {
        Set<LinkBean> setLinks = new LinkedHashSet<>();

        Elements elements = scannedDocument.select("a");
        for (Element element : elements) {
            String url = element.absUrl("href");
            if (!url.contains("#") && !url.isEmpty()) {
                LinkBean childLink = crawlerService.createLinkBean(url, link.getLinkDepth() + 1, link, seed);
                setLinks.add(childLink);
            }
        }

        return setLinks;
    }

    private String getTextFromPage(Document scannedDocument) {
        String text = "";
        Element body = scannedDocument.body();
        if (body != null) {
            text = body.text();
        }

        return text;
    }

    private Document getDocument(LinkBean link) {
        System.out.println(link);
        Document document = null;
        try {
            Connection con = Jsoup.connect(link.getName())
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                            "Chrome/89.0.4389.128 Safari/537.36 OPR/75.0.3969.243")
                    .timeout(10000);
            Connection.Response resp = con.ignoreHttpErrors(true).execute();

            if (resp.statusCode() == 200) {
                document = con.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    private static int getNumberOfMatches(String text, String searchWord) {
        int count = 0;
        Pattern pattern = Pattern.compile("(?i)\\b" + searchWord + "\\b", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            count++;
        }

        return count;
    }

    private String convertLink(String link) {
        return link.replaceAll("\\W", "").replaceAll("[_]", "");
    }
}

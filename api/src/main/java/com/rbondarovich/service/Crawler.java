package com.rbondarovich.service;

import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.impl.CrawlerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class Crawler {

    private final CrawlerServiceImpl crawlerService;
    private static final LinkBean NEXT_LEVEL_DEPTH = createConstantLinkBean();

    private static LinkBean createConstantLinkBean() {
        LinkBean constantLinkBean = new LinkBean();
        constantLinkBean.setName("NEXT_LEVEL_DEPTH");

        return constantLinkBean;
    }

    public void searchMatches(String link, String[] terms) throws IOException {
        searchMatches(link, terms, 8);
    }

    public void searchMatches(String link, String[] terms, int depth) throws IOException {
        searchMatches(link, terms, depth, 10000);
    }

    public void searchMatches(String link, String[] terms, int depth, int maxPages) throws IOException {
        String seed = convertLink(link);
        LinkBean seedLink = crawlerService.createLinkBean(link, null, seed);
        int currentDepth = 0;
        int currentPage = 0;
        Queue<LinkBean> links = new LinkedList<>();
        links.offer(seedLink);
        links.offer(NEXT_LEVEL_DEPTH);

        while (!links.isEmpty() || currentDepth < depth || currentPage < maxPages) {
            LinkBean linkBean = links.poll();
            if (linkBean.equals(NEXT_LEVEL_DEPTH)) {
                currentDepth++;
                links.offer(NEXT_LEVEL_DEPTH);
                continue;
            }
            crawlerService.saveLink(linkBean);
            LinkBean parentLink = crawlerService.getParentLink(linkBean);
            findAllLinks(parentLink, links, seed);

            String text = getTextFromPage(linkBean.getName());
            for (String term : terms) {
                int count = getNumberOfMatches(text, term);
                crawlerService.saveTerm(crawlerService.createWordCounterBean(term, count, parentLink));
            }
            currentPage++;
        }
    }

    private Queue<LinkBean> findAllLinks(LinkBean link, Queue<LinkBean> links, String seed) throws IOException {
        Set<LinkBean> setLinks = new LinkedHashSet<>();
//        Document doc = Jsoup.connect(link.getName()).get();
        Connection con = Jsoup.connect(link.getName())
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        "Chrome/89.0.4389.128 Safari/537.36 OPR/75.0.3969.243")
                .timeout(10000);
        Connection.Response resp = con.execute();
        Document doc;
        if (resp.statusCode() == 200) {
            doc = con.get();
            Elements elements = doc.select("a");
            for (Element element : elements) {
                String url = element.absUrl("href");
                if (!url.contains("#") && !url.isEmpty()) {
                    LinkBean childLink = crawlerService.createLinkBean(url, link, seed);
                    setLinks.add(childLink);
                }
            }
        }
        links.addAll(setLinks);
        return links;
    }

    private String getTextFromPage(String link) throws IOException {
        String text = "";

        Document document = Jsoup.connect(link).get();
        Element body = document.body();
        if (body != null) {
            text = body.text();
        }

        return text;
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
        return link.replaceAll("\\W", "");
    }
}

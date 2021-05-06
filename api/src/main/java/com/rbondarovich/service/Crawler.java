package com.rbondarovich.service;

import com.rbondarovich.service.bean.LinkBean;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class Crawler {

    private final CrawlerServiceImpl crawlerService;
    private static final LinkBean NEXT_LEVEL_DEPTH = createConstantLinkBean();

    private static LinkBean createConstantLinkBean() {
        LinkBean constantLinkBean = new LinkBean();
        constantLinkBean.setLink("NEXT_LEVEL_DEPTH");

        return constantLinkBean;
    }

    public void searchMatches(String link, String[] terms) throws IOException {
        searchMatches(link, terms, 8);
    }

    public void searchMatches(String link, String[] terms, int depth) throws IOException {
       searchMatches(link, terms, depth, 10000);
    }

    public void searchMatches(String link, String[] terms, int depth, int maxPages) throws IOException {
        LinkBean seed = crawlerService.createLinkBean(link, null);
        int currentDepth = 0;
        int currentPage = 0;
        Queue<LinkBean> links = new LinkedList<>();
        links.offer(seed);
        links.offer(NEXT_LEVEL_DEPTH);

        while (!links.isEmpty() || currentDepth < depth || currentPage < maxPages) {
            LinkBean linkBean = links.poll();
            if (linkBean.equals(NEXT_LEVEL_DEPTH)) {
                currentDepth++;
                links.offer(NEXT_LEVEL_DEPTH);
                continue;
            }
            findAllLinks(linkBean, links);
            crawlerService.saveLink(linkBean);
            String text = getTextFromPage(linkBean.getLink());
            for (String term : terms) {
                int count = getNumberOfMatches(text, term);
                crawlerService.saveTerm(crawlerService.createWordCounterBean(term, count, linkBean));
            }
            currentPage++;
        }
    }

    private Queue<LinkBean> findAllLinks(LinkBean link, Queue<LinkBean> links) throws IOException {
        Document doc = Jsoup.connect(link.getLink()).get();
        Elements elements = doc.select("a");
        for (Element element : elements) {
            String url = element.absUrl("href");
            if (!url.contains("#")) {
                LinkBean childLink = crawlerService.createLinkBean(url, link);
                links.add(childLink);
            }
        }
        return links;
    }

    private String getTextFromPage(String link) throws IOException {
        String text = "";

        Document document = Jsoup.connect(link).get();
        Element body = document.body();
        text = body.text();

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
}

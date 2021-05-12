package com.rbondarovich.service.interfaces;

public interface Crawler {

    void searchMatches(String link, String[] terms);

    void searchMatches(String link, int maxPages, String[] terms);

    void searchMatches(String link, String[] terms, int depth);

    void searchMatches(String link, String[] terms, int limitDepth, int maxPages);
}

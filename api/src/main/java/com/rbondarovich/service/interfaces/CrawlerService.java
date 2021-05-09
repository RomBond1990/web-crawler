package com.rbondarovich.service.interfaces;

import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.bean.WordCounterBean;

public interface CrawlerService {

    void saveLink(LinkBean linkBean);

    void saveTerm(WordCounterBean wordCounter);

    WordCounterBean createWordCounterBean(String term, int count, LinkBean link);

    LinkBean createLinkBean(String link, LinkBean parentLink, String seed);
}

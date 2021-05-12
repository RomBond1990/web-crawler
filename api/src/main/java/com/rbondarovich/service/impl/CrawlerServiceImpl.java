package com.rbondarovich.service.impl;

import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.bean.WordCounterBean;
import com.rbondarovich.service.interfaces.CrawlerService;
import com.rbondarovich.service.interfaces.LinkService;
import com.rbondarovich.service.interfaces.WordCounterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CrawlerServiceImpl implements CrawlerService {

    private final LinkService linkService;
    private final WordCounterService wordCounterService;


    public LinkBean getStoredLink(LinkBean parentLink) {
        return linkService.getLinkByName(parentLink.getName());
    }



    @Override
    public LinkBean createLinkBean(String link, Integer linkDepth, LinkBean parentLink, String seed) {
        LinkBean linkBean = new LinkBean();
        linkBean.setName(link);
        linkBean.setLinkDepth(linkDepth);
        linkBean.setParentLink(parentLink);
        linkBean.setSeed(seed);

        return linkBean;
    }

    @Override
    public WordCounterBean createWordCounterBean(String term, int count, LinkBean link) {
        WordCounterBean wordCounterBean = new WordCounterBean();
        wordCounterBean.setWord(term);
        wordCounterBean.setCount(count);
        wordCounterBean.setLink(link);

        return wordCounterBean;
    }

    @Override
    public void saveLink(LinkBean linkBean) {
        linkService.saveLink(linkBean);
    }

    @Override
    public void saveTerm(WordCounterBean wordCounter) {
        wordCounterService.saveWordCounter(wordCounter);
    }

}

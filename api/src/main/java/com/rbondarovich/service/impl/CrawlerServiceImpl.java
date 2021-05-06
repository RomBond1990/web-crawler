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

    @Override
    public LinkBean createLinkBean(String link, LinkBean parentLink){
        LinkBean linkBean = new LinkBean();
        linkBean.setLink(link);
        linkBean.setParentLink(parentLink);

        return linkBean;
    }

    @Override
    public WordCounterBean createWordCounterBean(String term, int count, LinkBean link){
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

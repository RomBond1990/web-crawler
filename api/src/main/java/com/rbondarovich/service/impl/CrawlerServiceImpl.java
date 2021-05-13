package com.rbondarovich.service.impl;

import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.bean.WordCounterBean;
import com.rbondarovich.service.interfaces.CrawlerService;
import com.rbondarovich.service.interfaces.LinkService;
import com.rbondarovich.service.interfaces.WordCounterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Used to create LinkBean and WordCounterBean, save and get data
 * @author Roman Bondarovich
 */
@Service
@AllArgsConstructor
public class CrawlerServiceImpl implements CrawlerService {

    private final LinkService linkService;
    private final WordCounterService wordCounterService;

    /**
     * Gets LinkBean by @param parentLink
     * @param parentLink the page from which the jump was made
     * @return LinkBean
     */
    public LinkBean getStoredLink(LinkBean parentLink) {
        return linkService.getLinkByName(parentLink.getName());
    }

    /**
     * Create LinkBean from data
     * @param link scanned page
     * @param linkDepth number of jumps from the home page
     * @param parentLink the page from which the jump was made
     * @param seed link consisting only of letters and numbers
     * @return LinkBean
     */
    @Override
    public LinkBean createLinkBean(String link, Integer linkDepth, LinkBean parentLink, String seed) {
        LinkBean linkBean = new LinkBean();
        linkBean.setName(link);
        linkBean.setLinkDepth(linkDepth);
        linkBean.setParentLink(parentLink);
        linkBean.setSeed(seed);

        return linkBean;
    }

    /**
     * Create WordCounterBean from data
     * @param term search word
     * @param count number of matches
     * @param link the page where the search took place
     * @return WordCounterBean
     */
    @Override
    public WordCounterBean createWordCounterBean(String term, int count, LinkBean link) {
        WordCounterBean wordCounterBean = new WordCounterBean();
        wordCounterBean.setWord(term);
        wordCounterBean.setCount(count);
        wordCounterBean.setLink(link);

        return wordCounterBean;
    }

    /**
     * Used @see LinkServiceImpl#saveLink(LinkBean linkBean)
     * @param linkBean
     */
    @Override
    public void saveLink(LinkBean linkBean) {
        linkService.saveLink(linkBean);
    }

    /**
     * Used @see WordCounterServiceImpl#saveWordCounter(WordCounterBean wordCounterBean)
     * @param wordCounter
     */
    @Override
    public void saveTerm(WordCounterBean wordCounter) {
        wordCounterService.saveWordCounter(wordCounter);
    }

}

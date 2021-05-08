package com.rbondarovich.service.impl;

import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.bean.TermStatsBean;
import com.rbondarovich.service.bean.WordCounterBean;
import com.rbondarovich.service.interfaces.LinkService;
import com.rbondarovich.service.interfaces.TermStatsService;
import com.rbondarovich.service.interfaces.WordCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TermStatsServiceImpl implements TermStatsService {

    private final WordCounterService wordCounterService;
    private final LinkService linkService;

    @Override
    public List<TermStatsBean> getTermsStatsForAllLinks(String seedLink) {
        List<TermStatsBean> termsStats;
        List<LinkBean> allLinks = linkService.getAllLinks(seedLink);
        termsStats = getAllLinksStats(allLinks);

        return termsStats;
    }

    @Override
    public List<TermStatsBean> getTermsStatsForTopLinks(String seedLink, Integer topLimit) {
        List<TermStatsBean> termsStats = new LinkedList<>();
        List<LinkBean> topLinks = linkService.getTopLinks(seedLink, topLimit);
        termsStats = getAllLinksStats(topLinks);

        return  termsStats;
        }

    private List<WordCounterBean> getStatsTerms(String link) {
        List<WordCounterBean> statsTerms = wordCounterService.getWordCountersByLink(link);

        return statsTerms;
    }

    private Integer calculateTotalMatches(Iterable<WordCounterBean> statsTerms) {
        int totalMatches = 0;
        for (WordCounterBean statTerm : statsTerms) {
            totalMatches = statTerm.getCount();
        }

        return totalMatches;
    }

    private TermStatsBean getLinkStats(String link) {
        TermStatsBean termStatsBean = new TermStatsBean();
        termStatsBean.setLink(link);
        List<WordCounterBean> statsTerms = getStatsTerms(link);
        termStatsBean.setStatsTerms(statsTerms);
        termStatsBean.setTotalMatches(calculateTotalMatches(statsTerms));

        return termStatsBean;
    }

    private List<TermStatsBean> getAllLinksStats(List<LinkBean> allLinks) {
        List<TermStatsBean> termsStats = new LinkedList<>();
        for (LinkBean link : allLinks) {
            TermStatsBean linkStats = getLinkStats(link.getName());
            termsStats.add(linkStats);
        }
        return termsStats;
    }

}

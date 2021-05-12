package com.rbondarovich.service.impl;

import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.bean.TermStatsBean;
import com.rbondarovich.service.bean.WordCounterBean;
import com.rbondarovich.service.interfaces.LinkService;
import com.rbondarovich.service.interfaces.TermStatsService;
import com.rbondarovich.service.interfaces.WordCounterService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

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
        List<TermStatsBean> termsStats = getTermsStatsForAllLinks(seedLink);
        termsStats.sort(Comparator.comparingInt(TermStatsBean::getTotalMatches).reversed());
        List<TermStatsBean> topLimitLinks = new ArrayList<>();
        for (int i = 0; i < topLimit; i++) {
            topLimitLinks.add(termsStats.get(i));
        }

        return topLimitLinks;
    }

    @Override
    public InputStreamResource getCSVResource(List<TermStatsBean> stats) {
        String[] tableHead = getTableHead(stats.get(0));
        List<List<String>> tableBody = getTableBody(stats);

        ByteArrayInputStream byteArrayOutputStream;
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(
                        new PrintWriter(out),
                        CSVFormat.DEFAULT.withHeader(tableHead)
                )
        ) {
            for (List<String> row : tableBody) {
                csvPrinter.printRecord(row);
            }
            csvPrinter.flush();
            byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return new InputStreamResource(byteArrayOutputStream);
    }

    private String[] getTableHead(TermStatsBean bean) {
        List<String> headList = new ArrayList<>();
        headList.add("URL");

        for (WordCounterBean wcbean : bean.getWordCounterBeans()) {

            headList.add(wcbean.getWord());
        }
        headList.add("total");

        return headList.toArray(new String[0]);
    }

    private List<List<String>> getTableBody(List<TermStatsBean> stats) {
        List<List<String>> tableBody = new ArrayList<>();

        for (TermStatsBean bean : stats) {
            List<String> row = new ArrayList<>();
            row.add(bean.getLink());
            for (WordCounterBean wcBean : bean.getWordCounterBeans()) {
                row.add(wcBean.getCount().toString());
            }
            row.add(bean.getTotalMatches().toString());
            tableBody.add(row);

        }

        return tableBody;
    }

    private List<WordCounterBean> getStatsTerms(LinkBean link) {

        return wordCounterService.getWordCountersByLink(link);
    }

    private Integer calculateTotalMatches(Iterable<WordCounterBean> statsTerms) {
        int totalMatches = 0;
        for (WordCounterBean statTerm : statsTerms) {
            totalMatches += statTerm.getCount();
        }

        return totalMatches;
    }

    private TermStatsBean getLinkStats(LinkBean link) {
        TermStatsBean termStatsBean = new TermStatsBean();
        termStatsBean.setLink(link.getName());
        termStatsBean.setId(link.getId());
        List<WordCounterBean> statsTerms = getStatsTerms(link);
        termStatsBean.setWordCounterBeans(statsTerms);
        termStatsBean.setTotalMatches(calculateTotalMatches(statsTerms));

        return termStatsBean;
    }

    private List<TermStatsBean> getAllLinksStats(List<LinkBean> allLinks) {
        List<TermStatsBean> termsStats = new LinkedList<>();
        for (LinkBean link : allLinks) {
            TermStatsBean linkStats = getLinkStats(link);
            termsStats.add(linkStats);
        }
        return termsStats;
    }

}

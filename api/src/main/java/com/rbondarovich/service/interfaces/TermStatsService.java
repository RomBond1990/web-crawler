package com.rbondarovich.service.interfaces;

import com.rbondarovich.service.bean.TermStatsBean;

import java.util.List;

public interface TermStatsService {

    List<TermStatsBean> getTermsStatsForAllLinks(String seedLink);

    List<TermStatsBean> getTermsStatsForTopLinks(String seedLink, Integer topLimit);
}

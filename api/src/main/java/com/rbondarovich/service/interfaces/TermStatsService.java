package com.rbondarovich.service.interfaces;

import com.rbondarovich.service.bean.TermStatsBean;
import org.springframework.core.io.InputStreamResource;

import java.util.List;

public interface TermStatsService {

    List<TermStatsBean> getTermsStatsForAllLinks(String seedLink);

    List<TermStatsBean> getTermsStatsForTopLinks(String seedLink, Integer topLimit);

    InputStreamResource getCSVResource(List<TermStatsBean> stats);
}

package com.rbondarovich.service.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermStatsBean {

    private String link;
    private List<WordCounterBean> statsTerms;
    private Integer totalMatches;
}

package com.rbondarovich.service.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermStatsBean {

    private Long id;
    private String link;
    private List<WordCounterBean> wordCounterBeans;
    private Integer totalMatches;
}

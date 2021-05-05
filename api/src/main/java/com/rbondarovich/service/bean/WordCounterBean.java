package com.rbondarovich.service.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordCounterBean {

    private Long id;
    private String word;
    private Integer count;
}

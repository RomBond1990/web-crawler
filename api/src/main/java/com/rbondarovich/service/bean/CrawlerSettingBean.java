package com.rbondarovich.service.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerSettingBean {

    private String link;

    private String[] terms;

    private Integer depth;

    private Integer maxPages;

}

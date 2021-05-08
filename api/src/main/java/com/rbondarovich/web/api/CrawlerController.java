package com.rbondarovich.web.api;

import com.rbondarovich.service.Crawler;
import com.rbondarovich.service.bean.CrawlerSettingBean;
import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.bean.TermStatsBean;
import com.rbondarovich.service.bean.WordCounterBean;
import com.rbondarovich.service.interfaces.TermStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "api/crawlers")
@RequiredArgsConstructor
public class CrawlerController {

    private final Crawler crawler;
    private final TermStatsService termStatsService;

    @GetMapping("/{seedLinks}")
    public ResponseEntity<List<TermStatsBean>> getTermsStatsForAllLinks(@PathVariable("seedLinks") String seedLink) {
        List<TermStatsBean> termsStatsForAllLinks = termStatsService.getTermsStatsForAllLinks(seedLink);
        ResponseEntity<List<TermStatsBean>> result = new ResponseEntity<>(termsStatsForAllLinks, HttpStatus.OK);

        return result;
    }

    @GetMapping("/{seedLinks}/{limit}")
    public ResponseEntity<List<TermStatsBean>> getTermsStatsForAllLinks(
            @PathVariable("seedLinks") String seedLink,
            @PathVariable("limit") Integer limit) {
        List<TermStatsBean> termsStatsForTopLinks = termStatsService.getTermsStatsForTopLinks(seedLink, limit);
        ResponseEntity<List<TermStatsBean>> result = new ResponseEntity<>(termsStatsForTopLinks, HttpStatus.OK);

        return result;
    }

    @PostMapping
    public ResponseEntity<CrawlerSettingBean> searchMatches(@RequestBody CrawlerSettingBean setting) throws IOException {
        if (setting.getDepth() != null) {
            crawler.searchMatches(setting.getLink(), setting.getTerms(), setting.getDepth());
        } else crawler.searchMatches(setting.getLink(), setting.getTerms());

        return new ResponseEntity<>(setting, HttpStatus.OK);
    }

}

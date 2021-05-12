package com.rbondarovich.web.api;

import com.rbondarovich.service.bean.CrawlerSettingBean;
import com.rbondarovich.service.bean.TermStatsBean;
import com.rbondarovich.service.interfaces.Crawler;
import com.rbondarovich.service.interfaces.TermStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

//    @GetMapping("/{seedLinks}")
//    public ResponseEntity<List<TermStatsBean>> getTermsStatsForAllLinks(
//            @PathVariable("seedLinks") String seedLink) throws IOException {
//
//        List<TermStatsBean> termsStatsForAllLinks = termStatsService.getTermsStatsForAllLinks(seedLink);
//        ResponseEntity<List<TermStatsBean>> result = new ResponseEntity<>(termsStatsForAllLinks, HttpStatus.OK);
//
//        return result;
//    }

    @GetMapping(value = "/{seedLinks}", produces = "text/csv")
    public ResponseEntity<Resource> getTermsStatsForAllLinks(
            @PathVariable("seedLinks") String seedLink) throws IOException {

        List<TermStatsBean> termsStatsForAllLinks = termStatsService.getTermsStatsForAllLinks(seedLink);

        return createCSVResponce(termsStatsForAllLinks);
    }

    @GetMapping(value = "/{seedLinks}/{limit}", produces = "text/csv")
    public ResponseEntity<Resource> getTermsStatsForTopLinks(
            @PathVariable("seedLinks") String seedLink,
            @PathVariable("limit") Integer limit) {

        List<TermStatsBean> termsStatsForTopLinks = termStatsService.getTermsStatsForTopLinks(seedLink, limit);

        return createCSVResponce(termsStatsForTopLinks);
    }

    @PostMapping
    public ResponseEntity<CrawlerSettingBean> searchMatches(
            @RequestBody CrawlerSettingBean setting) throws IOException {

        if (setting.getDepth() != null && setting.getMaxPages() != null) {
            crawler.searchMatches(setting.getLink(), setting.getTerms(), setting.getDepth(), setting.getMaxPages());
        } else if (setting.getDepth() != null && setting.getMaxPages() == null) {
            crawler.searchMatches(setting.getLink(), setting.getTerms(), setting.getDepth());
        } else if (setting.getDepth() == null && setting.getMaxPages() != null) {
            crawler.searchMatches(setting.getLink(), setting.getMaxPages(), setting.getTerms());
        } else crawler.searchMatches(setting.getLink(), setting.getTerms());

        return new ResponseEntity<>(setting, HttpStatus.OK);
    }


    private ResponseEntity<Resource> createCSVResponce(List<TermStatsBean> termStats) {
        InputStreamResource csvResource = termStatsService.getCSVResource(termStats);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stats.csv");
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(csvResource, headers, HttpStatus.OK);
    }

}

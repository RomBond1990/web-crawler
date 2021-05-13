package com.rbondarovich.web.api;

import com.rbondarovich.service.bean.CrawlerSettingBean;
import com.rbondarovich.service.bean.TermStatsBean;
import com.rbondarovich.service.exception.IncorrectInputData;
import com.rbondarovich.service.interfaces.Crawler;
import com.rbondarovich.service.interfaces.CrawlerService;
import com.rbondarovich.service.interfaces.TermStatsService;
import com.rbondarovich.web.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoint for web application
 */
@RestController
@RequestMapping(value = "api/crawlers")
@RequiredArgsConstructor
public class CrawlerController {

    private final Crawler crawler;
    private final TermStatsService termStatsService;

    /**
     * Returns all data in the СSV format
     *
     * @param seedLink root link consisting only of words and numbers
     * @return
     */
    @GetMapping(value = "/{seedLinks}", produces = "text/csv")
    public ResponseEntity<Resource> getTermsStatsForAllLinks(
            @PathVariable("seedLinks") String seedLink) {

        List<TermStatsBean> termsStatsForAllLinks = termStatsService.getTermsStatsForAllLinks(seedLink);
        return createCSVResponse(termsStatsForAllLinks);
    }

    /**
     * Returns the top (@param topLimit) of data by total matches in the СSV format
     *
     * @param seedLink root link consisting only of words and numbers
     * @param limit    number of records to select
     * @return ResponseEntity<Resource>
     */
    @GetMapping(value = "/{seedLinks}/{limit}", produces = "text/csv")
    public ResponseEntity<Resource> getTermsStatsForTopLinks(
            @PathVariable("seedLinks") String seedLink,
            @PathVariable("limit") Integer limit) {

        List<TermStatsBean> termsStatsForTopLinks = termStatsService.getTermsStatsForTopLinks(seedLink, limit);
        return createCSVResponse(termsStatsForTopLinks);
    }

    /**
     * Transmits the search criteria to the crawler
     *
     * @param settings search criteria
     * @return ResponseEntity<CrawlerSettingBean>
     */
    @PostMapping
    public ResponseEntity<CrawlerSettingBean> searchMatches(
            @RequestBody CrawlerSettingBean settings) throws IncorrectInputData {

        if (Validator.validateData(settings)) {
            if (settings.getDepth() != null && settings.getMaxPages() != null) {
                crawler.searchMatches(settings.getLink(),
                        settings.getTerms(),
                        Integer.parseInt(settings.getDepth()),
                        Integer.parseInt(settings.getMaxPages()));
            } else if (settings.getDepth() != null && settings.getMaxPages() == null) {
                crawler.searchMatches(settings.getLink(),
                        settings.getTerms(),
                        Integer.parseInt(settings.getDepth()));
            } else if (settings.getDepth() == null && settings.getMaxPages() != null) {
                crawler.searchMatches(settings.getLink(),
                        Integer.parseInt(settings.getMaxPages()),
                        settings.getTerms());
            } else crawler.searchMatches(settings.getLink(), settings.getTerms());

        } ;

        return new ResponseEntity<>(settings, HttpStatus.OK);
    }


    private ResponseEntity<Resource> createCSVResponse(List<TermStatsBean> termStats) {
        InputStreamResource csvResource = termStatsService.getCSVResource(termStats);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stats.csv");
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        return new ResponseEntity<>(csvResource, headers, HttpStatus.OK);
    }

}

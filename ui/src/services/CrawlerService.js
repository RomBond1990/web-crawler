import axios from 'axios';
import React from 'react';


const CRAWLER_API_BASE_URL = "http://localhost:8080/api/crawlers";

class CrawlerService  {

    getTermsStatsForAllLinks(seedLink) {
        return axios.get(CRAWLER_API_BASE_URL + "/" + seedLink);
    }

    getTermsStatsForTopLinks(seedLink, limit) {
        return axios.get(CRAWLER_API_BASE_URL + "/" + seedLink + "/" + limit);
    }

    createCrawlerSettings(settings) {
        return axios.post(CRAWLER_API_BASE_URL, settings);
    }
}

export default new CrawlerService();

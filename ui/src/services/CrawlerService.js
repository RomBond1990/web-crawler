import axios from 'axios';
import React from 'react';


const CRAWLER_API_BASE_URL = "http://localhost:8080/api/crawlers";
const FileDownload = require('js-file-download');

class CrawlerService {

    getTermsStatsForAllLinks(seedLink) {

        return axios({
            url: CRAWLER_API_BASE_URL + "/" + seedLink,
            method: 'GET',
            responseType: 'blob', // Important
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'file.csv'); //or any other extension
            document.body.appendChild(link);
            link.click();
        });
    }

    getTermsStatsForTopLinks(seedLink, limit) {
        return axios.get(CRAWLER_API_BASE_URL + "/" + seedLink + "/" + limit).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'file.csv'); //or any other extension
            document.body.appendChild(link);
            link.click();
        });

    }

    createCrawlerSettings(settings) {
        return axios.post(CRAWLER_API_BASE_URL, settings).catch((e) => {
            return e.response
        });
    }
}

export default new CrawlerService();

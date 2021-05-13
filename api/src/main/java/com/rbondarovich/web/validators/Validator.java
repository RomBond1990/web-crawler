package com.rbondarovich.web.validators;

import com.rbondarovich.service.bean.CrawlerSettingBean;
import com.rbondarovich.service.exception.IncorrectInputData;
import org.apache.commons.validator.routines.UrlValidator;

public class Validator {

    public static boolean validateURL(String link) {
        String[] schemes = {"http", "https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);

        return urlValidator.isValid(link);
    }

    public static boolean validateData(CrawlerSettingBean settings) throws IncorrectInputData {
        if (!validateURL(settings.getLink())) throw new IncorrectInputData("The value of \"URL\" is incorrect");
        String depth = settings.getDepth();
        String maxPages = settings.getMaxPages();
        try {
            Integer.parseInt(depth);
        } catch (NumberFormatException e) {
            throw new IncorrectInputData("The value of \"Depth\" is incorrect");
        }
        try {
            Integer.parseInt(maxPages);
        } catch (NumberFormatException e) {
            throw new IncorrectInputData("The value of \"Max scanned pages\" is incorrect");
        }
        if (settings.getTerms()[0].equals("") ) throw new IncorrectInputData("Enter the term");

        return true;
    }
}

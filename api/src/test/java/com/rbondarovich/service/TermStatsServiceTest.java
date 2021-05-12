package com.rbondarovich.service;

import com.rbondarovich.service.bean.TermStatsBean;
import com.rbondarovich.service.interfaces.TermStatsService;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = "/data-start.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/data-finish.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TermStatsServiceTest {

    @Autowired
    private TermStatsService termStatsService;

    @Test
    public void getTermsStatsForAllLinksTest() {
        List<TermStatsBean> statsBeans =
                termStatsService.getTermsStatsForAllLinks("httpsenwikipediaorgwikiElonMusk");
        Assert.assertTrue(statsBeans.get(0).getTotalMatches() == 411);
        Assert.assertEquals("https://en.wikipedia.org/wiki/Bachelor_of_Science", statsBeans.get(7).getLink());
        Assert.assertTrue(statsBeans.get(19).getTotalMatches() == 17);
    }

    @Test
    public void getTermsStatsForTopLinksTest() {
        List<TermStatsBean> statsBeans =
                termStatsService.getTermsStatsForTopLinks("httpsenwikipediaorgwikiElonMusk", 12);

        Assert.assertEquals("https://en.wikipedia.org/wiki/Tesla,_Inc.", statsBeans.get(0).getLink());
        Assert.assertTrue(statsBeans.get(0).getTotalMatches() == 976);
        Assert.assertEquals("https://en.wikipedia.org/wiki/Neuralink", statsBeans.get(6).getLink());
        Assert.assertTrue(statsBeans.get(6).getTotalMatches() == 46);
        Assert.assertEquals("https://en.wikipedia.org/wiki/Zip2", statsBeans.get(8).getLink());
        Assert.assertTrue(statsBeans.get(8).getTotalMatches() == 27);
    }

}

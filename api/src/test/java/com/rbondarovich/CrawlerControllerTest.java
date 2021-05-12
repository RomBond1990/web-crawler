package com.rbondarovich;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbondarovich.service.bean.CrawlerSettingBean;
import com.rbondarovich.service.bean.TermStatsBean;
import com.rbondarovich.service.interfaces.TermStatsService;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@NoArgsConstructor
@TestPropertySource("/application-test.properties")
//@Sql(value = "/data-start.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = "/data-finish.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CrawlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TermStatsService termStatsService;

//    public void getTermsStatsForAllLinksTest() throws Exception {
//
//        MockMultipartFile file = new MockMultipartFile()
//        this.mockMvc.perform(
//                MockMvcRequestBuilders.multipart("/api/crawlers/httpsbananabyindexphpnewsid290844/5")
//                        .file("file", "http://www.test.com/xml".getBytes())
//                        .param("name", "file.csv"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("{\"message\":\"Your document has queued for enhancement successfully.\",\"exception\":null}"));
//    }

    @Test
    public void searchMatchesTest() throws Exception {
        CrawlerSettingBean setting = new CrawlerSettingBean();
        setting.setLink("https://banana.by/index.php?newsid=290844");
        setting.setDepth(2);
        setting.setMaxPages(15);
        setting.setTerms(new String[]{"пиво", "по религиозным убеждениям"});

        String json = new ObjectMapper().writeValueAsString(setting);
        this.mockMvc.perform(post("/api/crawlers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json));

        ArrayList<TermStatsBean> top5TermsStats =
                (ArrayList<TermStatsBean>) termStatsService
                        .getTermsStatsForTopLinks("httpsbananabyindexphpnewsid290844", 5);
        int total = 0;
        for(TermStatsBean bean : top5TermsStats){
           total += bean.getTotalMatches();
        }

        Assert.assertEquals(total, 40);
        Assert.assertEquals(top5TermsStats.get(4).getLink(), "https://banana.by/index.php?do=about");
    }



}

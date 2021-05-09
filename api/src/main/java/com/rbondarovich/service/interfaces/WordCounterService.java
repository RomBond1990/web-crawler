package com.rbondarovich.service.interfaces;

import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.bean.WordCounterBean;

import java.util.List;

public interface WordCounterService {

    Iterable<WordCounterBean> getAllWordCounters();

    List<WordCounterBean> getWordCountersByLink(LinkBean link);

    WordCounterBean getWordCounterById(Long id);

    void saveWordCounter(WordCounterBean wordCounterBean);

    void deleteWordCounter(Long id);
}

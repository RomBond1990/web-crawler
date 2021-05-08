package com.rbondarovich.service.interfaces;

import com.rbondarovich.service.bean.WordCounterBean;

import java.util.List;

public interface WordCounterService {

    Iterable<WordCounterBean> getAllWordCounters();

    List<WordCounterBean> getWordCountersByLink(String link);

    WordCounterBean getWordCounterById(Long id);

    void saveWordCounter(WordCounterBean wordCounterBean);

    void deleteWordCounter(Long id);
}

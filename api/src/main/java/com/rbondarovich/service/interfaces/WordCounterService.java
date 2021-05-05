package com.rbondarovich.service.interfaces;

import com.rbondarovich.service.bean.WordCounterBean;

public interface WordCounterService {

    Iterable<WordCounterBean> getAllWordCounters();

    WordCounterBean getWordCounterById(Long id);

    void saveWordCounter(WordCounterBean wordCounterBean);

    void deleteWordCounter(Long id);
}

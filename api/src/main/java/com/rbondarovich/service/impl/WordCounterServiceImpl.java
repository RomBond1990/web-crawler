package com.rbondarovich.service.impl;

import com.rbondarovich.dao.entity.WordCounter;
import com.rbondarovich.dao.repository.WordCounterRepository;
import com.rbondarovich.service.bean.LinkBean;
import com.rbondarovich.service.bean.WordCounterBean;
import com.rbondarovich.service.interfaces.WordCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WordCounterServiceImpl implements WordCounterService {

    private final EntityBeanConverterImpl converter;
    private final WordCounterRepository wordCounterRepository;

    @Override
    public Iterable<WordCounterBean> getAllWordCounters() {
        List<WordCounter> wordCounters = wordCounterRepository.findAll();
        List<WordCounterBean> wordCounterBeans = converter.convertToBeanList(wordCounters, WordCounterBean.class);

        return wordCounterBeans;
    }

    @Override
    public List<WordCounterBean> getWordCountersByLink(LinkBean link) {
        List<WordCounter> wordCounters = wordCounterRepository.findByLink_Id(link.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Link not exist with id: " + link.getId()));
        List<WordCounterBean> wordCounterBeans = converter.convertToBeanList(wordCounters, WordCounterBean.class);

        return wordCounterBeans;
    }

    @Override
    public WordCounterBean getWordCounterById(Long id) {
        WordCounter wordCounter = wordCounterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WordCounter not exist with id: " + id));
        WordCounterBean wordCounterBean = converter.convertToBean(wordCounter, WordCounterBean.class);

        return wordCounterBean;
    }



    @Override
    public void saveWordCounter(WordCounterBean wordCounterBean) {
        WordCounter wordCounter = converter.convertToEntity(wordCounterBean, WordCounter.class);
        wordCounterRepository.save(wordCounter);
    }

    @Override
    public void deleteWordCounter(Long id) {
        wordCounterRepository.deleteById(id);
    }
}

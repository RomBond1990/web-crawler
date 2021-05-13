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

/**
 * Сlass for retrieving and saving word counters from the database
 * Implements WordCounterService
 * @author Roman Bondarovich
 */
@Service
@Transactional
@RequiredArgsConstructor
public class WordCounterServiceImpl implements WordCounterService {

    /**
     * To convert entities into bins and vice versa
     */
    private final EntityBeanConverterImpl converter;

    /**
     * Allows you to retrieve data from the database
     */
    private final WordCounterRepository wordCounterRepository;

    /**
     * Gets a list of all word counters from the database and converts it into a bean
     *
     * @return List<WordCounterBean>
     */
    @Override
    public List<WordCounterBean> getAllWordCounters() {
        List<WordCounter> wordCounters = wordCounterRepository.findAll();
        return converter.convertToBeanList(wordCounters, WordCounterBean.class);
    }

    /**
     * Gets a list of word counters from the database by parent link and converts it to a bean
     *
     * @param link where the words were found
     * @return List<WordCounterBean>
     * @throws ResourceNotFoundException if link not found
     */
    @Override
    public List<WordCounterBean> getWordCountersByLink(LinkBean link) {
        List<WordCounter> wordCounters = wordCounterRepository.findByLink_Id(link.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Link not exist with link: " + link.getId()));
        return converter.convertToBeanList(wordCounters, WordCounterBean.class);
    }

    /**
     * Gets word counter from the database by id and converts it to a bean
     *
     * @param id assigned in the database
     * @return WordCounterBean
     * @throws ResourceNotFoundException if id not found
     */
    @Override
    public WordCounterBean getWordCounterById(Long id) throws ResourceNotFoundException {
        WordCounter wordCounter = wordCounterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WordCounter not exist with id: " + id));
        return converter.convertToBean(wordCounter, WordCounterBean.class);
    }

    /**
     * Converts bean into a entity and saves it to database
     *
     * @param wordCounterBean
     */
    @Override
    public void saveWordCounter(WordCounterBean wordCounterBean) {
        WordCounter wordCounter = converter.convertToEntity(wordCounterBean, WordCounter.class);
        wordCounterRepository.save(wordCounter);
    }

    /**
     * Delete word counter by id
     * @param id assigned in the database
     */
    @Override
    public void deleteWordCounter(Long id) {
        wordCounterRepository.deleteById(id);
    }
}

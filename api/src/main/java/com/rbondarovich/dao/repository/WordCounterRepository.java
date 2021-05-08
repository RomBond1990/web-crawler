package com.rbondarovich.dao.repository;

import com.rbondarovich.dao.entity.WordCounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordCounterRepository extends JpaRepository<WordCounter, Long> {

    List<WordCounter>findByLink_Name(String link);
}

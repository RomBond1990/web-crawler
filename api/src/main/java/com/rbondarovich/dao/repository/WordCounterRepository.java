package com.rbondarovich.dao.repository;

import com.rbondarovich.dao.entity.WordCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordCounterRepository extends JpaRepository<WordCounter, Long> {
}

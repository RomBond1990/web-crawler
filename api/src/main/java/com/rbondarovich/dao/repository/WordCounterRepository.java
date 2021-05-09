package com.rbondarovich.dao.repository;

import com.rbondarovich.dao.entity.WordCounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordCounterRepository extends JpaRepository<WordCounter, Long> {

    List<WordCounter>findByLink_Name(String link);

    Optional<List<WordCounter>> findByLink_Id(Long linkId);
}

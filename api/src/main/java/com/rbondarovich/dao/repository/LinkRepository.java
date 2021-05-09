package com.rbondarovich.dao.repository;

import com.rbondarovich.dao.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<List<Link>> findBySeed(String seed);

    Optional<Link> findByName(String name);

}

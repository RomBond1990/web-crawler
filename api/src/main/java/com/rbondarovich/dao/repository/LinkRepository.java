package com.rbondarovich.dao.repository;

import com.rbondarovich.dao.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}

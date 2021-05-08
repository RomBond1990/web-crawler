package com.rbondarovich.dao.repository;

import com.rbondarovich.dao.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    List<Link> findBySeed(String seed);

    @Query(value = "SELECT * FROM Link l WHERE l.id IN " +
            "(SELECT wc.fk_link_id FROM WordCounter wc ORDER BY " +
            "   (SELECT SUM(wc.count) FROM WordCounter wc GROUP BY wc.fk_link_id)" +
            "DESC LIMIT ?1) and l.name = ?2", nativeQuery = true)
    List<Link> findTopByTotalMatches(Integer limit, String seed);

    Optional<Link> findByName(String name);
}

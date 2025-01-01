package com.grouphoki.energymonitoring.repository;

import com.grouphoki.energymonitoring.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByOrderByCreatedOnDesc();
}

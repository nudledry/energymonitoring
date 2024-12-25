package com.grouphoki.energymonitoring.repository;

import com.grouphoki.energymonitoring.models.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Integer> {
    void getAllNews();
}

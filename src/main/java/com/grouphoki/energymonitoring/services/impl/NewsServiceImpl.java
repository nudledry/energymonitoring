package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.models.News;
import com.grouphoki.energymonitoring.services.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl extends NewsService {
    @Override
    public List<News> getAllNews() {
        return List.of();
    }
}

package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.models.News;
import com.grouphoki.energymonitoring.repository.NewsRepository;
import com.grouphoki.energymonitoring.services.NewsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<News> getAllNews() {
        List<News> newsList = newsRepository.findAll();
        return newsList.stream().collect(Collectors.toList());
    }
}

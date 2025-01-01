package com.grouphoki.energymonitoring.services;

import com.grouphoki.energymonitoring.dto.NewsDto;
import java.util.List;

public interface NewsService {
    List<NewsDto> findAllNews();
    NewsDto findById(Long id);
    void createNews(NewsDto newsDto);
    void updateNews(NewsDto newsDto);
    void deleteNews(Long id);
}
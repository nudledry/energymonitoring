package com.grouphoki.energymonitoring.mapper;

import com.grouphoki.energymonitoring.dto.NewsDto;
import com.grouphoki.energymonitoring.models.News;

public class NewsMapper {
    public static NewsDto mapToNewsDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setContent(news.getContent());
        newsDto.setImageUrl(news.getImageUrl());
        newsDto.setSource(news.getSource());
        return newsDto;
    }

    public static News mapToNews(NewsDto newsDto) {
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setImageUrl(newsDto.getImageUrl());
        news.setSource(newsDto.getSource());
        return news;
    }
}

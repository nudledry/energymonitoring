package com.grouphoki.energymonitoring.services.impl;

import com.grouphoki.energymonitoring.dto.NewsDto;
import com.grouphoki.energymonitoring.mapper.NewsMapper;
import com.grouphoki.energymonitoring.models.News;
import com.grouphoki.energymonitoring.repository.NewsRepository;
import com.grouphoki.energymonitoring.services.NewsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.grouphoki.energymonitoring.mapper.NewsMapper.mapToNews;
import static com.grouphoki.energymonitoring.mapper.NewsMapper.mapToNewsDto;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<NewsDto> findAllNews() {
        List<News> news = newsRepository.findAllByOrderByCreatedOnDesc();
        return news.stream()
                .map(NewsMapper::mapToNewsDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDto findById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News not found"));
        return mapToNewsDto(news);
    }

    @Override
    public void createNews(NewsDto newsDto) {
        News news = mapToNews(newsDto);
        newsRepository.save(news);
    }

    @Override
    public void updateNews(NewsDto newsDto) {
        News news = newsRepository.findById(newsDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("News not found"));

        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setImageUrl(newsDto.getImageUrl());
        news.setSource(newsDto.getSource());

        newsRepository.save(news);
    }

    @Override
    public void deleteNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News not found"));
        newsRepository.delete(news);
    }
}
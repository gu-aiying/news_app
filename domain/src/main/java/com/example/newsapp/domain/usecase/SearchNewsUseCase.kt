package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.common.NewResult
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(query: String, page: Int = 1): NewResult<List<Article>> {
        return newsRepository.searchNews(query, page)
    }
}
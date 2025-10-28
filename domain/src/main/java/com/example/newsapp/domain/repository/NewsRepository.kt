package com.example.newsapp.domain.repository

import com.example.newsapp.domain.common.NewResult
import com.example.newsapp.domain.model.Article

interface NewsRepository {
    suspend fun getTopHeadlines(page: Int = 1): NewResult<List<Article>>
    suspend fun searchNews(query: String, page: Int = 1): NewResult<List<Article>>
}
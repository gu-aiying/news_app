package com.example.newsapp.data.repository

import com.example.newsapp.core.network.NewsApi
import com.example.newsapp.domain.common.NewResult
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository {

    override suspend fun getTopHeadlines(page: Int): NewResult<List<Article>> {
        return try {
            val response = newsApi.getTopHeadlines(page = page)
            if (response.status == "ok") {
                NewResult.Success(response.articles.map { it.toArticle() })
            } else {
                NewResult.Error("Failed to fetch news")
            }
        } catch (e: Exception) {
            NewResult.Error(e.message ?: "An unexpected error occurred")
        }
    }

    override suspend fun searchNews(query: String, page: Int): NewResult<List<Article>> {
        return try {
            val response = newsApi.searchNews(query, page = page)
            if (response.status == "ok") {
                NewResult.Success(response.articles.map { it.toArticle() })
            } else {
                NewResult.Error("Failed to search news")
            }
        } catch (e: Exception) {
            NewResult.Error(e.message ?: "An unexpected error occurred")
        }
    }

}
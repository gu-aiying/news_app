package com.example.newsapp.core.network.dto

import com.example.newsapp.domain.model.Article
import java.util.UUID

data class ArticleDto(
    val source: SourceDto,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
) {
    fun toArticle(): Article {
        return Article(
            id = UUID.nameUUIDFromBytes(url.toByteArray()).toString(),
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            source = source.name,
            content = content
        )
    }

}
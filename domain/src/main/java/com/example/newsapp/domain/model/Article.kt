package com.example.newsapp.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Article(
    val id: String = "",
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val source: String,
    val content: String? = null,
    val isBookmarked: Boolean = false
) {
    val formattedDate: String
        get() = try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(publishedAt)
            outputFormat.format(date ?: Date())
        } catch (e: Exception) {
            publishedAt
        }
}
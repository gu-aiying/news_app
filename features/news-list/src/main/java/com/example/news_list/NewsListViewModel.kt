package com.example.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.common.NewResult
import com.example.newsapp.domain.usecase.GetNewsUseCase
import com.example.newsapp.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsListUiState())
    val uiState: StateFlow<NewsListUiState> = _uiState.asStateFlow()

    private var currentPage = 1
    private var canLoadMore = true

    init {
        loadNews()
    }

    fun loadNews() {
        if (_uiState.value.isLoading || !canLoadMore) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            when (val result = getNewsUseCase(currentPage)) {
                is NewResult.Success -> {

                    val newArticles = if (currentPage == 1) {
                        result.data
                    } else {
                        _uiState.value.articles + result.data
                    }

                    canLoadMore = result.data.isNotEmpty()

                    _uiState.value = _uiState.value.copy(
                        articles = newArticles,
                        isLoading = false
                    )

                    currentPage++
                }

                is NewResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }

                NewResult.Loading -> {//do nothing, the screen state is already loading}
                }
            }
        }
    }

    fun refreshNews() {
        currentPage = 1
        canLoadMore = true
        _uiState.value = _uiState.value.copy(articles = emptyList())
        loadNews()
    }

    fun loadMoreNews() {
        loadNews()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

}

data class NewsListUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null
)
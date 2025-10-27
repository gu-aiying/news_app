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

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            when (val result = getNewsUseCase()) {
                is NewResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        articles = result.data,
                        isLoading = false,
                        error = null
                    )
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
        loadNews()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

}

data class NewsListUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
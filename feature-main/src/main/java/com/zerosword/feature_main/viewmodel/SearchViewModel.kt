package com.zerosword.feature_main.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.zerosword.domain.model.KakaoImageModel
import com.zerosword.domain.reporitory.KakaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val kakaoRepository: KakaoRepository,
) : ViewModel() {

    private val _errorMsg: MutableSharedFlow<String> = MutableStateFlow("")
    private val _searchQuery = MutableStateFlow("레진 코믹스 앱 로고")
    private val _imageSearchResults: MutableStateFlow<PagingData<KakaoImageModel.DocumentModel>> =
        MutableStateFlow(PagingData.empty())


    val errorMsg: SharedFlow<String> get() = _errorMsg
    val imageSearchResults: StateFlow<PagingData<KakaoImageModel.DocumentModel>> get() =
        _imageSearchResults.asStateFlow()
    val listState: LazyListState = LazyListState()

    init {
        println("init search viewmodel")

        viewModelScope.launch {
            _searchQuery
                .debounce(1000)
                .filter { it.isNotEmpty() }
                .flatMapLatest { query ->
                    kakaoRepository.searchImage(query = query, page = 1, size = 20)
                        .cachedIn(viewModelScope)
                        .catch {
                            println(it.message)
                            _errorMsg.emit("error")
                        }
                }
                .collect { pagingData ->
                    _imageSearchResults.value = pagingData
                }
        }

        viewModelScope.launch {
            imageSearchResults.collect {

            }
        }

    }

    fun searchImage(query: String) = viewModelScope.launch {
        _searchQuery.value = query
    }
}
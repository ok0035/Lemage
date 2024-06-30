package com.zerosword.feature_main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zerosword.domain.model.KakaoImageModel
import com.zerosword.domain.reporitory.KakaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val kakaoRepository: KakaoRepository
) : ViewModel() {

    private val _errorMsg: MutableSharedFlow<String> = MutableStateFlow("")
    val errorMsg: SharedFlow<String> get() = _errorMsg

    private val _imageSearchResults: MutableStateFlow<PagingData<KakaoImageModel.DocumentModel>?> =
        MutableStateFlow(null)
    val imageSearchResults: Flow<PagingData<KakaoImageModel.DocumentModel>>
        get() = _imageSearchResults.asStateFlow().filterNotNull()

    init {
        println("init viewmodel")
        viewModelScope.launch {
            searchImage("레진 코믹스 웹툰", page = 1)
        }
    }

    fun searchImage(query: String, page: Int) = viewModelScope.launch {
        kakaoRepository.searchImage(
            query = query,
            page = page,
            size = 40,
        )
            .cachedIn(viewModelScope)
            .collectLatest {
                _imageSearchResults.value = it
            }
    }
}
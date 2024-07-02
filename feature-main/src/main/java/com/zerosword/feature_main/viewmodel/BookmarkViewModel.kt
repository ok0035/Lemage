package com.zerosword.feature_main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerosword.domain.entity.FavoriteEntity
import com.zerosword.domain.reporitory.FavoriteRepository
import com.zerosword.domain.reporitory.KakaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val _errorMsg: MutableSharedFlow<String> = MutableStateFlow("")
    val errorMsg: SharedFlow<String> get() = _errorMsg

    private val _favoritesByKeyword = MutableStateFlow<Map<String, List<FavoriteEntity>>>(emptyMap())
    val favoritesByKeyword: StateFlow<Map<String, List<FavoriteEntity>>> get() = _favoritesByKeyword

    init {
        loadFavoritesByKeyword()
    }

    private fun loadFavoritesByKeyword() = viewModelScope.launch {
        val allFavorites = favoriteRepository.getAllFavorites()
        val groupedFavorites = allFavorites.groupBy { it.keyword }
        _favoritesByKeyword.value = groupedFavorites
    }

}
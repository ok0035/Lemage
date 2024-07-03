package com.zerosword.feature_main.viewmodel

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerosword.data.network.state.NetworkConnection
import com.zerosword.data.database.entity.FavoriteEntity
import com.zerosword.domain.entity.FavoriteModel
import com.zerosword.domain.reporitory.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val connection: NetworkConnection,
) : ViewModel() {

    private var _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> get() = _isConnected.asStateFlow()

    private val _errorMsg: MutableSharedFlow<String> = MutableStateFlow("")
    val errorMsg: SharedFlow<String> get() = _errorMsg

    private val _favoritesByKeyword = MutableStateFlow<Map<String, List<FavoriteModel>>>(emptyMap())
    val favoritesByKeyword: StateFlow<Map<String, List<FavoriteModel>>> get() = _favoritesByKeyword

    val gridState = LazyStaggeredGridState()

    init {
        viewModelScope.launch {
            connection.isConnected.collectLatest { isConnected ->
                _isConnected.value = isConnected
                if (isConnected) {
                    loadFavoritesByKeyword()
                }
            }
        }
    }

    fun loadFavoritesByKeyword() = viewModelScope.launch {
        val allFavorites = favoriteRepository.getAllFavorites()
        val groupedFavorites = allFavorites.groupBy { it.keyword }
        _favoritesByKeyword.value = groupedFavorites
    }

}
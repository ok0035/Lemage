package com.zerosword.feature_main.viewmodel

import android.util.Log
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zerosword.data.network.state.NetworkConnection
import com.zerosword.domain.entity.FavoriteModel
import com.zerosword.domain.state.KakaoImageSortState
import com.zerosword.domain.model.KakaoImageModel.DocumentModel
import com.zerosword.domain.reporitory.FavoriteRepository
import com.zerosword.domain.reporitory.KakaoRepository
import com.zerosword.domain.state.ToastState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val kakaoRepository: KakaoRepository,
    private val favoriteRepository: FavoriteRepository,
    private val connection: NetworkConnection,
) : ViewModel() {

    private var _currentQuery = "레진 코믹스 로고"
    private val _toastState: MutableSharedFlow<ToastState> = MutableSharedFlow(0)
    private val _searchQuery = MutableStateFlow(_currentQuery)
    private var _sortType = KakaoImageSortState.ACCURACY
    private var _isConnected = MutableStateFlow(true)
    private val _startPage = 1
    private val _pageSize = 20
    private val _imageSearchResults: MutableStateFlow<PagingData<DocumentModel>> =
        MutableStateFlow(PagingData.empty())

    val toastState: SharedFlow<ToastState> get() = _toastState.asSharedFlow()
    val searchQuery: StateFlow<String> get() = _searchQuery.asStateFlow()
    val sortType get() = _sortType.value
    val listState: LazyStaggeredGridState = LazyStaggeredGridState()
    val isConnected: StateFlow<Boolean> get() = _isConnected.asStateFlow()
    val imageSearchResults: StateFlow<PagingData<DocumentModel>>
        get() = _imageSearchResults.asStateFlow()

    init {

        viewModelScope.launch {
            connection.isConnected.collectLatest { isConnected ->
                _isConnected.value = isConnected
                if (isConnected) {
                    refreshList()
                        .collect { pagingData ->
                            _imageSearchResults.value = pagingData
                        }
                }
            }
        }

        viewModelScope.launch {
            searchQuery
                .debounce(1000)
                .filter { it.isNotEmpty() }
                .flatMapLatest { query ->
                    _currentQuery = query
                    refreshList(_currentQuery)
                }
                .collect { pagingData ->
                    _imageSearchResults.value = pagingData
                }
        }

    }

    private suspend fun refreshList(query: String = _currentQuery) =
        kakaoRepository.searchImage(
            query = query,
            page = _startPage,
            size = _pageSize,
            sort = sortType
        ) { errorMsg ->
            viewModelScope.launch {
                if (errorMsg.isNotEmpty()) _toastState.emit(ToastState.API_ERROR)
            }
        }.cachedIn(viewModelScope)

    fun searchImage(query: String) = viewModelScope.launch {
        _searchQuery.value = query
    }

    fun showToast(state: ToastState) = viewModelScope.launch {
        _toastState.emit(state)
    }

    fun addToFavoriteItem(keyword: String, imageUrl: String) = viewModelScope.launch {
        favoriteRepository.insert(FavoriteModel(keyword = keyword, imageUrl = imageUrl))
    }

    fun deleteFavoriteItem(keyword: String, imageUrl: String) = viewModelScope.launch {
        favoriteRepository.delete(keyword, imageUrl)
    }

    fun allFavoriteList() = viewModelScope.launch {
        favoriteRepository.getAllFavorites().forEachIndexed { index, favoriteEntity ->
            Log.d("BOOKMARK", "$index ${favoriteEntity.imageUrl}")
        }
    }

    fun isFavorite(keyword: String, imageUrl: String, favoriteState: Boolean): StateFlow<Boolean> {
        val isFavorite = MutableStateFlow(favoriteState)
        viewModelScope.launch {
            isFavorite.value = favoriteRepository.isFavorite(keyword, imageUrl)
        }
        return isFavorite
    }

    fun isFavorite(imageUrl: String, favoriteState: Boolean): StateFlow<Boolean> {
        val isFavorite = MutableStateFlow(favoriteState)
        viewModelScope.launch {
            isFavorite.value = favoriteRepository.isFavorite(imageUrl)
        }
        return isFavorite
    }
}
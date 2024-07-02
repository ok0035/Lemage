package com.zerosword.feature_main.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zerosword.data.network.state.NetworkConnection
import com.zerosword.domain.state.KakaoImageSortState
import com.zerosword.domain.model.KakaoImageModel
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
    private val connection: NetworkConnection,
) : ViewModel() {

    private var _currentQuery = "레진 코믹스 앱 로고"
    private val _toastState: MutableSharedFlow<ToastState> = MutableSharedFlow(0)
    private val _searchQuery = MutableStateFlow(_currentQuery)
    private var _sortType = KakaoImageSortState.ACCURACY
    private var _isConnected = MutableStateFlow(false)
    private val _imageSearchResults: MutableStateFlow<PagingData<KakaoImageModel.DocumentModel>> =
        MutableStateFlow(PagingData.empty())
    private val _startPage = 1
    private val _pageSize = 20

    val toastState: SharedFlow<ToastState> get() = _toastState.asSharedFlow()
    val sortType get() = _sortType.value
    val imageSearchResults: StateFlow<PagingData<KakaoImageModel.DocumentModel>>
        get() =
            _imageSearchResults.asStateFlow()
    val listState: LazyListState = LazyListState()
    val isConnected : StateFlow<Boolean> get() = _isConnected.asStateFlow()

    init {
        println("init search viewmodel")

        viewModelScope.launch {
            connection.isConnected.collectLatest { isConnected ->
                _isConnected.value = isConnected
                if (isConnected) {
                    refreshList().collect { pagingData -> _imageSearchResults.value = pagingData }
                }
            }
        }

        viewModelScope.launch {
            _searchQuery
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

    fun error(state: ToastState) = viewModelScope.launch {
        _toastState.emit(state)
    }
}
package com.zerosword.feature_main.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerosword.data.network.state.NetworkConnection
import com.zerosword.domain.entity.FavoriteModel
import com.zerosword.domain.reporitory.FavoriteRepository
import com.zerosword.domain.state.ToastState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val connection: NetworkConnection,
) : ViewModel() {

    private var _isConnected = MutableStateFlow(true)
    private val _toastState: MutableSharedFlow<ToastState> = MutableSharedFlow(0)
    private val _favoritesByKeyword = MutableStateFlow<Map<String, List<FavoriteModel>>>(emptyMap())
    private val _deleteItemList = MutableStateFlow<List<FavoriteModel>>(emptyList())

    val isConnected: StateFlow<Boolean> get() = _isConnected.asStateFlow()
    val favoritesByKeyword: StateFlow<Map<String, List<FavoriteModel>>> get() = _favoritesByKeyword
    val toastState: SharedFlow<ToastState> get() = _toastState.asSharedFlow()
    val deleteItemList get() = _deleteItemList.asStateFlow()
    val listState = LazyListState()

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

    private fun showToast(state: ToastState) = viewModelScope.launch {
        _toastState.emit(state)
    }

    fun loadFavoritesByKeyword() = viewModelScope.launch {
        val allFavorites = favoriteRepository.getAllFavorites()
        val groupedFavorites = allFavorites.groupBy { it.keyword }
        _favoritesByKeyword.value = groupedFavorites
    }

    fun addItemToDeleteList(model: FavoriteModel) = viewModelScope.launch {
        _deleteItemList.value += model
    }

    fun deleteItemToDeleteList(model: FavoriteModel) = viewModelScope.launch {
        if (deleteItemList.value.contains(model))
            _deleteItemList.value -= model
    }

    fun findItem(keyword: String, imageUrl: String): StateFlow<FavoriteModel?> {
        val stateFlow = MutableStateFlow<FavoriteModel?>(null)
        viewModelScope.launch {
            stateFlow.value = favoriteRepository.getFavorite(keyword, imageUrl)
        }
        return stateFlow.asStateFlow()
    }

    fun deleteItems() = viewModelScope.launch {
        val state =
            if (deleteItemList.value.isEmpty())
                ToastState.NO_DELETABLE_ITEMS
            else ToastState.DELETED_SELECTED_ITEM_FROM_BOOKMARK

        showToast(state)

        deleteItemList.value.forEach { model ->
            favoriteRepository.delete(model.keyword, model.imageUrl)
        }
        _deleteItemList.value = emptyList()
        loadFavoritesByKeyword()
    }

}
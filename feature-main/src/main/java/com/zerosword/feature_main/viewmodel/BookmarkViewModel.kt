package com.zerosword.feature_main.viewmodel

import androidx.lifecycle.ViewModel
import com.zerosword.domain.reporitory.KakaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val kakaoRepository: KakaoRepository,
) : ViewModel() {

    private val _errorMsg: MutableSharedFlow<String> = MutableStateFlow("")
    val errorMsg: SharedFlow<String> get() = _errorMsg

    init {
        println("init bookmark viewmodel")
    }

}
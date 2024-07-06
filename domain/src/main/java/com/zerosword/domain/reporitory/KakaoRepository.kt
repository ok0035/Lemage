package com.zerosword.domain.reporitory

import androidx.paging.PagingData
import com.zerosword.domain.model.KakaoImageModel
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {

    suspend fun searchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int,
        onApiError: (errorMsg: String) -> Unit
    ): Flow<PagingData<KakaoImageModel.DocumentModel>>
}
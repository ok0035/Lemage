package com.zerosword.domain.reporitory

import androidx.paging.PagingData
import com.zerosword.domain.model.KakaoImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {

    suspend fun searchImage(
        query: String,
        sort: String = "accuracy",
        page: Int,
        size: Int,
    ): Flow<PagingData<KakaoImageModel.DocumentModel>>
}
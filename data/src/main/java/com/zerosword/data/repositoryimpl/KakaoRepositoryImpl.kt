package com.zerosword.data.repositoryimpl

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.zerosword.data.network.state.NetworkConnection
import com.zerosword.data.paging.KakaoImagePagingSource
import com.zerosword.data.network.response.toDomainModel
import com.zerosword.data.services.KakaoService
import com.zerosword.domain.model.KakaoImageModel
import com.zerosword.domain.reporitory.KakaoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KakaoRepositoryImpl @Inject constructor(
    private val kakaoService: KakaoService,
) : KakaoRepository {

    override suspend fun searchImage(
        query: String,
        sort: String,
        page: Int,
        size: Int,
        onApiError: (errorMsg: String) -> Unit
    ): Flow<PagingData<KakaoImageModel.DocumentModel>> {

        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                KakaoImagePagingSource(kakaoService, query, sort) { errorMsg ->
                    onApiError(errorMsg)
                }
            }
        ).flow

    }

}
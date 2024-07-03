package com.zerosword.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnFailure
import com.zerosword.data.network.response.toDomainModel
import com.zerosword.data.services.KakaoService
import com.zerosword.domain.model.KakaoImageModel

class KakaoImagePagingSource(
    private val kakaoService: KakaoService,
    private val query: String,
    private val sort: String,
    private val onError: (errorMsg: String) -> Unit = {}
) : PagingSource<Int, KakaoImageModel.DocumentModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KakaoImageModel.DocumentModel> {
        return try {
            val page = params.key ?: 1
            val response = kakaoService.searchImage(
                query = query,
                sort = sort,
                page = page,
                size = params.loadSize,
            ).suspendOnFailure {
                onError(this.message())
            }

            val model = response.getOrNull()?.toDomainModel()
                ?: return LoadResult.Error(Exception("Empty response"))

            LoadResult.Page(
                data = model.documents,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (model.meta?.isEnd == true) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, KakaoImageModel.DocumentModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
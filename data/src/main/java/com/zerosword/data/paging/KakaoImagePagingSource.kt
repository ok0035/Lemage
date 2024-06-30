package com.zerosword.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skydoves.sandwich.getOrNull
import com.zerosword.data.response.toDomainModel
import com.zerosword.data.services.KakaoService
import com.zerosword.domain.model.KakaoImageModel

class KakaoImagePagingSource(
    private val kakaoService: KakaoService,
    private val query: String,
    private val sort: String
) : PagingSource<Int, KakaoImageModel.DocumentModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KakaoImageModel.DocumentModel> {
        return try {
            val page = params.key ?: 1
            val model = kakaoService.searchImage(
                query = query,
                sort = sort,
                page = page,
                size = params.loadSize,
            ).getOrNull()?.toDomainModel() ?: return LoadResult.Page(listOf(), null, null)

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
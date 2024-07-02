package com.zerosword.data.repositoryimpl

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.zerosword.data.database.dao.FavoriteDao
import com.zerosword.data.network.state.NetworkConnection
import com.zerosword.data.paging.KakaoImagePagingSource
import com.zerosword.data.response.toDomainModel
import com.zerosword.data.services.KakaoService
import com.zerosword.domain.entity.FavoriteEntity
import com.zerosword.domain.model.KakaoImageModel
import com.zerosword.domain.reporitory.FavoriteRepository
import com.zerosword.domain.reporitory.KakaoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override suspend fun isFavorite(favorite: FavoriteEntity): Boolean {
        return favoriteDao.isFavoriteExists(favorite.keyword, favorite.imageUrl)
    }

    override suspend fun insert(favorite: FavoriteEntity) {
        favoriteDao.insertFavorite(favorite)
    }

    override suspend fun delete(favorite: FavoriteEntity) {
        favoriteDao.deleteFavorite(favorite)
    }

    override suspend fun getAllFavorites(): List<FavoriteEntity> {
        return favoriteDao.getAllFavorites()
    }

    override suspend fun getFavoritesByKeyword(keyword: String): List<FavoriteEntity> {
        return favoriteDao.getFavoritesByKeyword(keyword)
    }

}
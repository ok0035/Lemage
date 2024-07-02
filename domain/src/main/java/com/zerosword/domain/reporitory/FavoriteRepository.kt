package com.zerosword.domain.reporitory

import androidx.paging.PagingData
import com.zerosword.domain.entity.FavoriteEntity
import com.zerosword.domain.model.KakaoImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun isFavorite(favorite: FavoriteEntity): Boolean

    suspend fun insert(favorite: FavoriteEntity)

    suspend fun delete(favorite: FavoriteEntity)

    suspend fun getAllFavorites(): List<FavoriteEntity>

    suspend fun getFavoritesByKeyword(keyword: String): List<FavoriteEntity>

}
package com.zerosword.domain.reporitory

import com.zerosword.domain.entity.FavoriteModel

interface FavoriteRepository {

    suspend fun isFavorite(keyword: String, imageUrl: String): Boolean

    suspend fun insert(favorite: FavoriteModel)

    suspend fun delete(keyword: String, imageUrl: String)

    suspend fun getAllFavorites(): List<FavoriteModel>

    suspend fun getFavoritesByKeyword(keyword: String): List<FavoriteModel>

}
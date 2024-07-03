package com.zerosword.data.repositoryimpl

import com.zerosword.data.database.dao.FavoriteDao
import com.zerosword.data.database.entity.FavoriteEntity
import com.zerosword.data.database.entity.toDomainModel
import com.zerosword.data.database.entity.toEntity
import com.zerosword.domain.entity.FavoriteModel
import com.zerosword.domain.reporitory.FavoriteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override suspend fun isFavorite(keyword: String, imageUrl: String): Boolean {
        return favoriteDao.isFavoriteExists(keyword, imageUrl)
    }

    override suspend fun insert(favorite: FavoriteModel) {
        favoriteDao.insertFavorite(favorite.toEntity())
    }

    override suspend fun delete(keyword: String, imageUrl: String) {
        favoriteDao.deleteFavorite(keyword, imageUrl)
    }

    override suspend fun getAllFavorites(): List<FavoriteModel> {
        return favoriteDao.getAllFavorites().toDomainModel()
    }

    override suspend fun getFavoritesByKeyword(keyword: String): List<FavoriteModel> {
        return favoriteDao.getFavoritesByKeyword(keyword).toDomainModel()
    }

}
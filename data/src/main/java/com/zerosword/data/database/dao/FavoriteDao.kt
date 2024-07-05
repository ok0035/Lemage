package com.zerosword.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zerosword.data.database.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE imageUrl = :imageUrl AND keyword = :keyword")
    suspend fun deleteFavorite(keyword: String, imageUrl: String)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteEntity>

    @Query("SELECT * FROM favorites WHERE keyword = :keyword")
    suspend fun getFavoritesByKeyword(keyword: String): List<FavoriteEntity>

    @Query("SELECT * FROM favorites WHERE keyword = :keyword AND imageUrl = :imageUrl LIMIT 1")
    suspend fun getFavorite(keyword: String, imageUrl: String): FavoriteEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE keyword = :keyword AND imageUrl = :imageUrl)")
    suspend fun isFavoriteExists(keyword: String, imageUrl: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE imageUrl = :imageUrl)")
    suspend fun isFavoriteExists(imageUrl: String): Boolean
}
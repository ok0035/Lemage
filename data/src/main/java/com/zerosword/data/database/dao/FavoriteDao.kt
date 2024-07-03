package com.zerosword.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zerosword.domain.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE imageUrl = :imageUrl AND keyword = :keyword")
    suspend fun deleteFavorite(keyword: String, imageUrl: String)

    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteEntity>

    @Query("SELECT DISTINCT keyword FROM favorites")
    suspend fun getAllKeywords(): List<String>

    @Query("SELECT * FROM favorites WHERE keyword = :keyword")
    suspend fun getFavoritesByKeyword(keyword: String): List<FavoriteEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE keyword = :keyword AND imageUrl = :imageUrl)")
    suspend fun isFavoriteExists(keyword: String, imageUrl: String): Boolean
}
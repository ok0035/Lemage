package com.zerosword.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zerosword.data.database.dao.FavoriteDao
import com.zerosword.domain.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
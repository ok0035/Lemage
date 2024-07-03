package com.zerosword.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class FavoriteModel(
    val keyword: String,
    val imageUrl: String,
    var isSelect: Boolean = false
)

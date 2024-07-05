package com.zerosword.domain.entity

data class FavoriteModel(
    val keyword: String,
    val imageUrl: String,
    var isSelect: Boolean = false
)

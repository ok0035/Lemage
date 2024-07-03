package com.zerosword.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zerosword.domain.entity.FavoriteModel

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val keyword: String,
    val imageUrl: String
)

fun FavoriteEntity.toDomainModel() = FavoriteModel(
    keyword = keyword,
    imageUrl = imageUrl
)

fun FavoriteModel.toEntity() = FavoriteEntity(
    keyword = keyword,
    imageUrl = imageUrl
)

fun List<FavoriteEntity>.toDomainModel(): List<FavoriteModel> {
    return map {
        FavoriteModel(
            keyword = it.keyword,
            imageUrl = it.imageUrl
        )
    }

}

fun List<FavoriteModel>.toEntity(): List<FavoriteEntity> {
    return map {
        FavoriteEntity(
            keyword = it.keyword,
            imageUrl = it.imageUrl
        )
    }
}


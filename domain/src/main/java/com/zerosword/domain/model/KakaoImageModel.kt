package com.zerosword.domain.model

data class KakaoImageModel(
    val documents: List<DocumentModel> = listOf(),
    val meta: MetaModel? = null
) {
    data class DocumentModel(
        val imageUrl: String = "", // http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg\
        var isFavorite: Boolean = false
    )

    data class MetaModel(
        val isEnd: Boolean = false, // false
        val pageableCount: Int = 0, // 3854
        val totalCount: Int = 0 // 422583
    )
}
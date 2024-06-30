package com.zerosword.domain.model

data class KakaoImageModel(
    val documents: List<DocumentModel> = listOf(),
    val meta: MetaModel? = null
) {
    data class DocumentModel(
        val collection: String? = "", // news
        val datetime: String? = "", // 2017-06-21T15:59:30.000+09:00
        val displaySiteName: String? = "", // 한국경제TV
        val docUrl: String? = "", // http://v.media.daum.net/v/20170621155930002
        val height: Int? = 0, // 457
        val imageUrl: String? = "", // http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg
        val thumbnailUrl: String? = "", // https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp
        val width: Int? = 0 // 540
    )

    data class MetaModel(
        val isEnd: Boolean? = false, // false
        val pageableCount: Int? = 0, // 3854
        val totalCount: Int? = 0 // 422583
    )
}
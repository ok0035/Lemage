package com.zerosword.data.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.zerosword.domain.model.KakaoImageModel

@Keep
@Parcelize
data class KakaoImageResponse(
    @SerializedName("documents")
    val documents: List<DocumentRes>? = listOf(),
    @SerializedName("meta")
    val meta: MetaRes? = MetaRes()
) : Parcelable {
    @Keep
    @Parcelize
    data class DocumentRes(
        @SerializedName("collection")
        val collection: String? = "", // news
        @SerializedName("datetime")
        val datetime: String? = "", // 2017-06-21T15:59:30.000+09:00
        @SerializedName("display_sitename")
        val displaySiteName: String? = "", // 한국경제TV
        @SerializedName("doc_url")
        val docUrl: String? = "", // http://v.media.daum.net/v/20170621155930002
        @SerializedName("height")
        val height: Int? = 0, // 457
        @SerializedName("image_url")
        val imageUrl: String? = "", // http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg
        @SerializedName("thumbnail_url")
        val thumbnailUrl: String? = "", // https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp
        @SerializedName("width")
        val width: Int? = 0 // 540
    ) : Parcelable

    @Keep
    @Parcelize
    data class MetaRes(
        @SerializedName("is_end")
        val isEnd: Boolean? = false, // false
        @SerializedName("pageable_count")
        val pageableCount: Int? = 0, // 3854
        @SerializedName("total_count")
        val totalCount: Int? = 0 // 422583
    ) : Parcelable
}

fun KakaoImageResponse.DocumentRes.toDomainModel(): KakaoImageModel.DocumentModel =
    KakaoImageModel.DocumentModel(
        collection = collection,
        datetime = datetime,
        displaySiteName = displaySiteName,
        docUrl = docUrl,
        height = height,
        width = width,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl
    )

fun KakaoImageResponse.MetaRes.toDomainModel(): KakaoImageModel.MetaModel =
    KakaoImageModel.MetaModel(
        isEnd, pageableCount, totalCount
    )

fun KakaoImageResponse.toDomainModel(): KakaoImageModel {

    val documentsModel = this.documents?.map {
        it.toDomainModel()
    } ?: listOf()

    val metaModel = this.meta?.toDomainModel()

    return KakaoImageModel(
        documents = documentsModel,
        meta = metaModel
    )
}
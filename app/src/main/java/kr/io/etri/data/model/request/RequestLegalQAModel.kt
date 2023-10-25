package kr.io.etri.data.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.File


/**
 * etri
 * Created by Naedong
 * Date: 2023-10-20(020)
 * Time: 오전 10:18
 */

@JsonClass(generateAdapter = true)
data class RequestLegalQAModel(
    @Json(name = "access_key")
    val accessKey :String,
    @Json(name = "argument")
    val argument : RequestLegalObject
)
@JsonClass(generateAdapter = true)
data class RequestLegalObject(
    @Json(name = "question")
    val question: String
)

@JsonClass(generateAdapter = true)
data class RequestLanguageModel(
    @Json(name = "access_key")
    val accessKey :String,
    @Json(name = "argument")
    val argument : RequestLegalObject
)
@JsonClass(generateAdapter = true)
data class RequestLanguageObject(
    @Json(name = "language_code")
    val languageCode : String,
    @Json(name = "audio")
    val audio: String
)
package kr.io.etri.data.model.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


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
package kr.io.etri.data.model.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ResponseLegalQAModel(
    @Json(name = "result")
    val result: Int,
    @Json(name = "return_object")
    val returnObject: ResponseReturnObject
)
@JsonClass(generateAdapter = true)
data class ResponseReturnObject(
    @Json(name = "LegalInfo")
    val legalInfo: ResponseLegalInfo?
)

@JsonClass(generateAdapter = true)
data class ResponseLegalInfo(
    @Json(name = "AnswerInfo")
    val answerInfo: List<ResponseAnswerInfo>?,
    @Json(name = "RelatedQs")
    val relatedQs: List<String>?
)

@JsonClass(generateAdapter = true)
data class ResponseAnswerInfo(
    @Json(name = "answer")
    val answer: String?,
    @Json(name = "clause")
    val clause: String?,
    @Json(name = "confidence")
    val confidence: Double?,
    @Json(name = "rank")
    val rank: Int?,
    @Json(name = "source")
    val source: String?
)
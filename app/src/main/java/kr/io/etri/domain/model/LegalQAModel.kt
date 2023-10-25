package kr.io.etri.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-20(020)
 * Time: 오후 1:34
 */

@Serializable
data class LegalQAModel(
    val result: Int,
    val returnObject: ReturnObject
)
@Serializable
data class ReturnObject(
    val legalInfo: LegalInfo?
)
@Serializable
data class LegalInfo(
    val answerInfo: List<AnswerInfo>?,
    val relatedQs: List<String>?
)
@Serializable
data class AnswerInfo(
    @SerialName("answer")
    val answer : String?,
    @SerialName("clause")
    val clause : String?,
    @SerialName("confidence")
    val confidence : Double?,
    @SerialName("rank")
    val rank : Int?,
    @SerialName("source")
    val source : String?
)
package kr.io.etri.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-20(020)
 * Time: 오후 1:34
 */

data class LegalQAModel(
    val result: Int,
    val returnObject: ReturnObject
)
data class ReturnObject(
    val legalInfo: LegalInfo
)

data class LegalInfo(
    val answerInfo: List<AnswerInfo>,
    val relatedQs: List<String>
)

data class AnswerInfo(
    val answer: String,
    val clause: String,
    val confidence: Double,
    val rank: Int,
    val source: String
)
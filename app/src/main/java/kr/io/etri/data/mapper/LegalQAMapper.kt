package kr.io.etri.data.mapper

import kr.io.etri.data.model.response.ResponseAnswerInfo
import kr.io.etri.data.model.response.ResponseLegalInfo
import kr.io.etri.data.model.response.ResponseLegalQAModel
import kr.io.etri.data.model.response.ResponseReturnObject
import kr.io.etri.domain.model.AnswerInfo
import kr.io.etri.domain.model.LegalInfo
import kr.io.etri.domain.model.LegalQAModel
import kr.io.etri.domain.model.ReturnObject
import retrofit2.Response

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-20(020)
 * Time: 오후 1:36
 */

fun Response<ResponseLegalQAModel>.asLegal() : Response<LegalQAModel> {
    return Response.success(body()?.let {
        LegalQAModel(
            it.result,
            it.returnObject.asLegal()
        )
    })
}



fun ResponseReturnObject.asLegal() : ReturnObject {
    return ReturnObject(
        legalInfo?.asLegal()
    )
}

fun ResponseLegalInfo.asLegal() : LegalInfo {
    return LegalInfo(
        answerInfo?.map { it.asLegal() },
        relatedQs
    )
}



fun ResponseAnswerInfo.asLegal() : AnswerInfo {
    return AnswerInfo(
        answer,
        clause,
        confidence,
        rank,
        source
    )

}
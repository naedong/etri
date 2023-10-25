package kr.io.etri.data.mapper

import android.util.Log
import kr.io.etri.common.exception.ResponseCall
import kr.io.etri.common.exception.Result
import kr.io.etri.data.model.response.ResponseAnswerInfo
import kr.io.etri.data.model.response.ResponseLegalInfo
import kr.io.etri.data.model.response.ResponseLegalQAModel
import kr.io.etri.data.model.response.ResponseReturnObject
import kr.io.etri.data.repositoryimpl.getEmit
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


fun Result<ResponseLegalQAModel>.asLegal(response: Result<ResponseLegalQAModel>) : LegalQAModel {
    return when(response) {
        is Result.Success -> LegalQAModel( returnObject = response.data.returnObject.asLegal(),
            result = response.data.result)
        is Result.ApiError -> getEmit()
        else -> getEmit()
    }
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
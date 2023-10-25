package kr.io.etri.data.api

import kr.io.etri.BuildConfig
import kr.io.etri.common.exception.Result
import kr.io.etri.data.model.request.RequestLegalObject
import kr.io.etri.data.model.request.RequestLegalQAModel
import kr.io.etri.data.model.response.ResponseLegalQAModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-20(020)
 * Time: 오전 10:15
 */
interface ApiService {

    @POST("/LegalQA")
    suspend fun getLegalQa(
        @Body requset : RequestLegalQAModel
    ) : Result<ResponseLegalQAModel>


    @POST("/Audio")
    suspend fun getAudio(
        @Body requset : RequestLegalQAModel
    ) : Result<ResponseLegalQAModel>
}
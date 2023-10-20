package kr.io.etri.data.remote

import kr.io.etri.data.api.ApiService
import kr.io.etri.data.mapper.asLegal
import kr.io.etri.data.model.request.RequestLegalObject
import kr.io.etri.data.model.request.RequestLegalQAModel
import kr.io.etri.domain.model.LegalQAModel
import retrofit2.Response
import javax.inject.Inject

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-20(020)
 * Time: 오후 1:33
 */
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {


    suspend fun getLegalQA(accessKey : String, legalObject : RequestLegalObject ) : Response<LegalQAModel> {
        return apiService.getLegalQa(RequestLegalQAModel(accessKey, legalObject)).asLegal()
    }


}
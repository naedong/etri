package kr.io.etri.domain.usecase

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.io.etri.data.model.request.RequestLegalObject
import kr.io.etri.domain.model.LegalQAModel
import kr.io.etri.domain.repository.LegalQARepository
import javax.inject.Inject

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-20(020)
 * Time: 오후 1:46
 */
class LegalQAUseCase @Inject constructor(
    val repository : LegalQARepository
) {

    fun getLegalQAUseCase(request : RequestLegalObject) : Flow<LegalQAModel> {

        return repository.invoke(request)
    }

}
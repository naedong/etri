package kr.io.etri.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kr.io.etri.data.model.request.RequestLegalObject
import kr.io.etri.domain.model.LegalQAModel

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-20(020)
 * Time: 오후 1:46
 */
fun interface LegalQARepository : ( RequestLegalObject ) ->  Flow<LegalQAModel>
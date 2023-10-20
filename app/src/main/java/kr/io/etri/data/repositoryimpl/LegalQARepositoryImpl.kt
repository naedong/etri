package kr.io.etri.data.repositoryimpl

import android.util.Log
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kr.io.etri.data.model.request.RequestLegalObject
import kr.io.etri.data.remote.RemoteDataSource
import kr.io.etri.domain.model.LegalQAModel
import kr.io.etri.domain.repository.LegalQARepository
import javax.inject.Inject

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-20(020)
 * Time: 오후 1:48
 */
class LegalQARepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource,
) : LegalQARepository {
    override fun invoke(p1: RequestLegalObject): Flow<LegalQAModel> {
        return flow {
            dataSource.getLegalQA("", p1).body()?.let {
                emit(it)
            }
        }
    }
}
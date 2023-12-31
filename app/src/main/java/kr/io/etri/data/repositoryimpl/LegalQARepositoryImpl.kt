package kr.io.etri.data.repositoryimpl


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.io.etri.data.model.request.RequestLegalObject
import kr.io.etri.data.remote.RemoteDataSource
import kr.io.etri.domain.model.AnswerInfo
import kr.io.etri.domain.model.LegalInfo
import kr.io.etri.domain.model.LegalQAModel
import kr.io.etri.domain.model.ReturnObject
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
                    val data = dataSource.getLegalQA("", p1)
                    data?.runCatching {
                            this
                    }?.onSuccess {
                        emit(it)
                    }?.onFailure {
                        emit(getEmit())
                    }?.getOrDefault(getEmit())
                }
            }

        }



fun getEmit() = LegalQAModel(
    200,
    returnObject = ReturnObject(
        LegalInfo(
            answerInfo = listOf(
                AnswerInfo(
                    answer = "죄송합니다. \n 저는 한국전자통신연구원의 데이터만 가지고 있습니다. \n 자세한 문의는 한국전자통신연구원에서 문의바랍니다.",
                    null,
                    null,
                    null,
                    null,
                )
            ),
            null,
        )
    )
)
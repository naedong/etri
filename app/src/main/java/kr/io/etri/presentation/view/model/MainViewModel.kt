package kr.io.etri.presentation.view.model

import android.util.Base64
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.io.etri.R
import kr.io.etri.data.model.request.RequestLegalObject
import kr.io.etri.data.repositoryimpl.getEmit
import kr.io.etri.domain.model.LegalQAModel
import kr.io.etri.domain.usecase.LegalQAUseCase
import java.io.File
import javax.inject.Inject

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-19(019)
 * Time: 오후 2:43
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: LegalQAUseCase,
) : ViewModel() {

    fun SetRecodeData(stringData: List<String>) {
        viewModelScope.launch {
            _RecodeData.emit(stringData)
        }
    }


    fun sendLegalQA(string: String) {
        viewModelScope.launch {
            val myChat = ChatUiModel.Message(string, ChatUiModel.Author.me)

            _conversation.emit(_conversation.value + myChat)

            val botQAChat = useCase.getLegalQAUseCase(RequestLegalObject(string)).stateIn(
                CoroutineScope(coroutineContext)
            )

            delay(300L)


            botQAChat.runCatching {
                this.value
            }.onSuccess { model ->
                val models = model.returnObject.legalInfo?.answerInfo
                repeat(models?.size ?: 0) { count ->
                    if (models?.get(count)?.rank == 1) {
                        _conversation.emit(
                            _conversation.value +
                                    ChatUiModel.Message(
                                        "네 답변은 다음과 같습니다. \n\n"
                                                + models?.get(count)?.answer + "\n\n"
                                                + models?.get(count)?.clause + "\n\n"
                                                + "출처 : " + models?.get(count)?.source + "\n",
                                        ChatUiModel.Author.bot
                                    )
                        )
                    }
                }
            }.onFailure {
                _conversation.emit(
                    _conversation.value
                            + ChatUiModel.Message(
                        "죄송합니다. 그러한 내용은 없습니다. 자세한 사항은 홈페이지를 참고해주세요.",
                        ChatUiModel.Author.bot
                    )
                )
            }.getOrDefault(
                if (botQAChat?.value?.returnObject?.legalInfo?.answerInfo?.size == 0) {
                    _conversation.emit(
                        _conversation.value
                                + ChatUiModel
                            .Message(
                                getEmit()?.returnObject?.legalInfo?.answerInfo?.get(0)?.answer
                                    ?: "",
                                ChatUiModel.Author.bot
                            )
                    )
                } else {

                }
            )
        }
    }


    val conversation: StateFlow<List<ChatUiModel.Message>>
        get() = _conversation
    private val _conversation = MutableStateFlow(
        listOf(ChatUiModel.Message.initConv)
    )

    val recodeData: StateFlow<List<String>>
        get() = _RecodeData
    private val _RecodeData = MutableStateFlow(
        listOf("")
    )

    val recordeFile: LiveData<File>?
        get() = _RecordeFile
    private var _RecordeFile: MutableLiveData<File>? = null

    fun setRecordeFile(it: File) {
        _RecordeFile?.value = it
    }

    fun getRecordeFile() {
        val fileBytes = recordeFile?.value?.readBytes()
        val file = Base64.encodeToString(fileBytes ?: null, Base64.DEFAULT)


    }

}
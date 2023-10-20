package kr.io.etri.presentation.view.model

import android.util.Log
import androidx.compose.runtime.collectAsState
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
import kr.io.etri.data.model.request.RequestLegalObject
import kr.io.etri.domain.model.LegalQAModel
import kr.io.etri.domain.usecase.LegalQAUseCase
import javax.inject.Inject

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-19(019)
 * Time: 오후 2:43
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase : LegalQAUseCase
) : ViewModel() {


    fun sendLegalQA(string: String) {
        viewModelScope.launch {
            val myChat = ChatUiModel.Message(string, ChatUiModel.Author.me)

            _conversation.emit(_conversation.value + myChat)
            val botQAChat = useCase.getLegalQAUseCase(RequestLegalObject(string)).stateIn(
                CoroutineScope(coroutineContext))

            delay(300L)

            botQAChat.runCatching {
                this.value
            }.onSuccess {model ->
                repeat(model.returnObject.legalInfo.answerInfo.size){ count ->
                    _conversation.emit(_conversation.value + ChatUiModel.Message(botQAChat.value.returnObject.legalInfo.answerInfo[count].answer, ChatUiModel.Author.bot))
                }
            }.onFailure {
                _conversation.emit(_conversation.value + ChatUiModel.Message("죄송합니다." +
                        " 이해를 하지 못하였습니다." +
                        " 다시 작성해 주시길 바랍니다.", ChatUiModel.Author.bot))
            }
        }
    }







    val conversation: StateFlow<List<ChatUiModel.Message>>
        get() = _conversation
    private val _conversation = MutableStateFlow(
        listOf(ChatUiModel.Message.initConv)
    )

    private val questions = mutableListOf(
        "어제 어땟어??",
        "이건 어떤가요?",
        "이 문제의 답은 이것입니다.",
        "ChatGPT를 사용해주시길 바랍니다.",
        "ClovaX??"
    )

    fun sendChat(msg: String) {

        val myChat = ChatUiModel.Message(msg, ChatUiModel.Author.me)
        viewModelScope.launch {
            _conversation.emit(_conversation.value + myChat)
            delay(1000)
            _conversation.emit(_conversation.value + getRandomQuestion())
        }
    }


    private fun getRandomQuestion(): ChatUiModel.Message {
        val question = if (questions.isEmpty()) {
            "no further questions, please leave me alone"
        } else {
            questions.random()
        }

        if (questions.isNotEmpty()) questions.remove(question)

        return ChatUiModel.Message(
            text = question,
            author = ChatUiModel.Author.bot
        )
    }

}
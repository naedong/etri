package kr.io.etri.presentation.view.main


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import kr.io.etri.presentation.view.chat.ChatScreen
import kr.io.etri.presentation.view.model.ChatUiModel
import kr.io.etri.presentation.view.model.MainViewModel
import kr.io.etri.ui.theme.EtriTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EtriTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewmodel = hiltViewModel<MainViewModel>()
                    val conversation = viewmodel.conversation.collectAsState()

                    ChatScreen(model = ChatUiModel(conversation.value, addressee = ChatUiModel.Author.bot ),
                        { msg -> viewmodel.sendLegalQA(msg)},
                        modifier = Modifier
                        )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EtriTheme {
        ChatScreen(
            model = ChatUiModel(
            messages = listOf(
                ChatUiModel.Message(
                    "안녕하세요 무엇을 도와드릴까요?",
                    ChatUiModel.Author("0", "Branch")
                ),
                ChatUiModel.Message(
                    "궁금한게 생겼어 뭐든 질문 가능해?",
                    ChatUiModel.Author("-1", "Tree"))
            ),
            addressee = ChatUiModel.Author("0", "Branch")
        ),
            onSendChatClickListener = {},
            modifier = Modifier
        )
    }
}
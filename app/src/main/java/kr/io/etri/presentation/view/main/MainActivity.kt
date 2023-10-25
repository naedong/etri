package kr.io.etri.presentation.view.main


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
                        modifier = Modifier,
                        viewmodel
                        )

                    LaunchedEffect(mutableList){
                        viewmodel.SetRecodeData(mutableList)
                    }
                }
            }
        }
    }


    private val mutableList = mutableListOf("")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            mutableList.addAll(result ?: emptyList())
        }
    }
}






@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val viewmodel = hiltViewModel<MainViewModel>()
    EtriTheme {
        ChatScreen(
            model = ChatUiModel(
            messages = listOf(
                ChatUiModel.Message(
                    "안녕하세요 무엇을 도와드릴까요?",
                    ChatUiModel.Author("0", "Branch")
                ),
                ChatUiModel.Message(
                    "범죄에 대해 알려줘",
                    ChatUiModel.Author("-1", "Tree")),
                        ChatUiModel.Message(
                        "가. 「특정강력범죄의 처벌에 관한 특례법」 제2조의 범죄",
                ChatUiModel.Author("0", "Branch")
            ),
            ),
            addressee = ChatUiModel.Author("0", "Branch")
        ),
            onSendChatClickListener = {},
            modifier = Modifier,
            viewmodel = viewmodel
        )
    }
}

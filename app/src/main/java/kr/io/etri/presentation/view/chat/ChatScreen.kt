package kr.io.etri.presentation.view.chat

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kr.io.etri.common.extension.findActivity
import kr.io.etri.common.item.ChatItem
import kr.io.etri.common.permission.AndroidAudioRecorder
import kr.io.etri.common.permission.HandlePermissionAction
import kr.io.etri.presentation.view.model.ChatUiModel
import kr.io.etri.presentation.view.model.MainViewModel
import kr.io.etri.ui.theme.Black
import kr.io.etri.ui.theme.ChatBoxBackGround
import kr.io.etri.ui.theme.ChatRoomWhite
import java.io.File
import java.util.Locale

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-19(019)
 * Time: 오전 11:08
 */
@Composable
fun ChatScreen(
    model: ChatUiModel,
    onSendChatClickListener: (String) -> Unit,
    modifier: Modifier,
    viewmodel: MainViewModel,
) {
    val listState = rememberLazyListState()
    ConstraintLayout(
        constraintSettings(),
        modifier = modifier
            .fillMaxSize()
            .background(ChatRoomWhite),
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .layoutId("lazyColumn"),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(model.messages) { items ->
                ChatItem(message = items)
            }
        }
        ChatCommondLine(onSendChatClickListener, viewmodel)
    }
}



@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChatCommondLine(
    onSendChatClickListener: (String) -> Unit,
    viewmodel: MainViewModel,
) {

    val buttonCheck = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val recorder by lazy {
        AndroidAudioRecorder(context)
    }

    val coroutineScope = rememberCoroutineScope()
    val showPermissionDialog = remember {
        mutableStateOf(false)
    }
    val permissions = listOfNotNull(
        Manifest.permission.RECORD_AUDIO
    )

    val permissionState = rememberMultiplePermissionsState(permissions = permissions)


    HandlePermissionAction(
        permissionState = permissionState,
        showPermissionDialog = showPermissionDialog
    )

    var chatValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

//    ShowRecording(
//        coroutineScope,
//        chatValue,
//        context,
//        viewmodel,
//        recorder,
//    ) { buttonCheck.value = false }
//

    Row(
        modifier = Modifier
            .layoutId("row")
            .fillMaxWidth(1f)
            .background(ChatBoxBackGround),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {


        TextField(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    BorderStroke(width = 2.dp, color = Black),
                    shape = RoundedCornerShape(50)
                )
                .clip(
                    RoundedCornerShape(50)
                )
                .layoutId("textField"),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            value = chatValue, onValueChange = { chatValue = it },
            placeholder = {

                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Text(text = "입력하시오")

                    Icon(

                        imageVector = Icons.Default.Add,
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch {
                                    if (showPermissionDialog.value) {
                                        buttonCheck.value = true
                                    } else {
                                        showPermissionDialog.value = true
                                    }
                                }
                            },
                        tint = Color.Black,
                        contentDescription = ""
                    )

                }
            },
        )
        ElevatedButton(
            onClick = {
                val msg = chatValue.text
                if (msg.isBlank()) return@ElevatedButton
                onSendChatClickListener(chatValue.text)
                chatValue = TextFieldValue("")
            },
            modifier = Modifier
                .layoutId("sendButton")
                .padding(5.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 0.dp,
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                disabledContainerColor = Color.Cyan
            ),

            ) {
            Icon(
                imageVector = Icons.Default.Send,
                modifier = Modifier
                    .layoutId("buttonIcon"),
                tint = Color.Black,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun ShowRecording(
    coroutineScope: CoroutineScope,
    chatValue: TextFieldValue,
    context: Context,
    viewmodel: MainViewModel,
    recorder: AndroidAudioRecorder,
    onDismiss : () -> Unit
) {

    val cacheDir = context.cacheDir

    AlertDialog(
        modifier = Modifier,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
        confirmButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        File(cacheDir, "audio.mp3").runCatching {
                            this
                        }.onSuccess {
                            recorder.start(it)
                            viewmodel.setRecordeFile(it)
                        }.onFailure {
                            it
                        }
                    }
                }
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "녹음",
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        recorder.stop()
                        onDismiss()

                    }
                }
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "취소",
                )
            }
        },
        title = {

            Text(text = "Title Recorde")
        },
        text = {
            Text(text = "Recorde")
        }
    )

}

fun constraintSettings(): ConstraintSet = ConstraintSet {
    val lazyColumn = createRefFor("lazyColumn")
    val row = createRefFor("row")
    constrain(lazyColumn) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(row.top)
        height = Dimension.fillToConstraints
    }
    constrain(row) {
        top.linkTo(lazyColumn.bottom, 8.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        bottom.linkTo(parent.bottom)
    }


}

private fun getSpeechInput(context: Context) {
    // on below line we are checking if speech
    // recognizer intent is present or not.
    if (!SpeechRecognizer.isRecognitionAvailable(context)) {
        // if the intent is not present we are simply displaying a toast message.
        Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
    } else {
        // on below line we are calling a speech recognizer intent
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        // on the below line we are specifying language model as language web search
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
        )

        // on below line we are specifying extra language as default english language
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        // on below line we are specifying prompt as Speak something
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")

        // at last we are calling start activity
        // for result to start our activity.

        startActivityForResult(context.findActivity(), intent, 101, Bundle())
    }
}


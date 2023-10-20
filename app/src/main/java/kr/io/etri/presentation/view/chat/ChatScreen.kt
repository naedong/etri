package kr.io.etri.presentation.view.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import kr.io.etri.common.item.ChatItem
import kr.io.etri.presentation.view.model.ChatUiModel
import kr.io.etri.ui.theme.Black
import kr.io.etri.ui.theme.ChatBoxBackGround
import kr.io.etri.ui.theme.ChatRoomWhite
import kr.io.etri.ui.theme.PurpleGrey80

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
    modifier: Modifier
) {


    val listState = rememberLazyListState()
    ConstraintLayout(
        ConstraintSetting(), modifier = modifier
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
        ChatCommondLine(onSendChatClickListener)
    }
}


@Composable
fun ChatCommondLine(
    onSendChatClickListener: (String) -> Unit
) {
    var chatValue by remember {
        mutableStateOf(TextFieldValue(""))
    }
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
            value = chatValue, onValueChange = { chatValue = it }, placeholder = { Text(text = "입력하시오") },

            )


        ElevatedButton(
            onClick = {
                val msg = chatValue.text
                if (msg.isBlank()) return@ElevatedButton
                onSendChatClickListener(chatValue.text)
                chatValue = TextFieldValue("")

            },
            modifier = Modifier.layoutId("sendButton"),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 0.dp,
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                disabledContainerColor = Color.Cyan
            )
        ) {
            Icon(imageVector = Icons.Default.Send,

                modifier = Modifier
                    .layoutId("buttonIcon"),
                tint = Color.Black,
                contentDescription = "")

        }

    }

}


fun ConstraintSetting(): ConstraintSet {
    return ConstraintSet {
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
}
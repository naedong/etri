package kr.io.etri.common.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kr.io.etri.presentation.view.model.ChatUiModel
import kr.io.etri.ui.theme.ChatBoxBackGround
import kr.io.etri.ui.theme.PurpleGrey80

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-19(019)
 * Time: 오전 11:32
 */
@Composable
fun ChatItem(message : ChatUiModel.Message) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)) {
        Box(
            modifier = Modifier
                .align(if (message.isFromMe) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (message.isFromMe) 48f else 0f,
                        bottomEnd = if (message.isFromMe) 0f else 48f
                    )
                )
                .background(if (message.isFromMe) ChatBoxBackGround else Color.Blue.copy(0.2f))
                .padding(16.dp)
        ) {
            Text(text = message.text)
        }
        Box(
            modifier = Modifier
                .align(if (message.isFromMe) Alignment.End else Alignment.Start)
                .padding(1.dp)
        ) {
            Text(text = "${nowDate.getOrNull()}")
        }
    }
}

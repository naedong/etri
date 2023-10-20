package kr.io.etri.presentation.view.model

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-19(019)
 * Time: 오전 11:07
 */
data class ChatUiModel(
    val messages: List<Message>,
    val addressee: Author,
) {
    data class Message(
        val text: String,
        val author: Author,
    ) {
        val isFromMe: Boolean
            get() = author.id == MY_ID

        companion object {
            val initConv = Message(
                text = "안녕하세요 무엇을 도와드릴까요?",
                author = Author.bot
            )
        }
    }

    data class Author(
        val id: String,
        val name: String
    ) {
        companion object {
            val bot = Author("1", "Bot")
            val me = Author(MY_ID, "Me")
        }
    }

    companion object {
        const val MY_ID = "-1"
    }
}
package kr.io.etri.common.item

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Locale.*
import java.util.TimeZone

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-23(023)
 * Time: 오전 9:18
 */

fun String.getTimeNow(): Result<String> {
    return kotlin.runCatching {
        val simpleDateFormat = SimpleDateFormat(this, KOREAN)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        simpleDateFormat
    }.map {
        val date = Date(System.currentTimeMillis())
        it.format(date)
    }.onFailure {
        throw it
    }
}


var nowDate: Result<String> = "MM월 dd일 E요일 a hh:mm".getTimeNow()
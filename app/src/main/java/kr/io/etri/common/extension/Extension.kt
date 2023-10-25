package kr.io.etri.common.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-24(024)
 * Time: 오전 9:21
 */
fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}
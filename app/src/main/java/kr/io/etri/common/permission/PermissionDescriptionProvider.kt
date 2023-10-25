package kr.io.etri.common.permission

import android.content.Context

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-23(023)
 * Time: 오후 10:55
 */




interface PermissionDescriptionProvider {

    fun getTitle(context : Context) : String

    fun getDescription(context: Context, isPermanentlyDeclined : Boolean) : String
}

class RecodeAudioPermissionDescriptionProvider : PermissionDescriptionProvider {
    override fun getTitle(context: Context): String = "권한 설정을 해주시길 바랍니다.."

    override fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String {
        val baseDescription = "음성 인식을 하기 위해 알림을 허용 해주시길 바랍니다."
        return if(isPermanentlyDeclined){
            "$baseDescription\n${"[설정] -> [알림]에서 권한을 설정해주세요."}"
        } else {
            baseDescription
        }
    }
}
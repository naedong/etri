package kr.io.etri


import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kr.io.etri.data.api.ApiService
import kr.io.etri.data.model.request.RequestLegalObject
import kr.io.etri.data.remote.RemoteDataSource
import okhttp3.Call
import okhttp3.Dispatcher
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest : BaseTest() {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }



    @Before
    fun setUp() {
        mockLogClass()

    }
    @Test
    fun `ApiService Request및 Response 데이터 확인`() {
        val okHttp = OkHttpClient().newBuilder()
            .callTimeout(30, TimeUnit.SECONDS) // 호출 타이머
            .connectTimeout(30, TimeUnit.SECONDS) // 연결 타이머
            .readTimeout(200, TimeUnit.SECONDS) // 읽기 타이머
            .writeTimeout(30, TimeUnit.SECONDS) // 쓰기 타이머
            .addInterceptor { chain ->   // 사용자 커스텀 인터셉터
                val original = chain.request()
                    .newBuilder()
                    .addHeader(
                        "Authorization",
                        BuildConfig.API_KEY
                    )
                    .build()

                val originalHttpUrl: HttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
//                    .addQueryParameter(
//                        "serviceKey",
//                        ""
//                    )
                    .build()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)

                val newRequest: Request = requestBuilder.build()

                val client = OkHttpClient.Builder()
                    .dispatcher(Dispatcher().apply { maxRequestsPerHost = 1 })
                    .build()

                client.newCall(newRequest).enqueue(object : okhttp3.Callback {

                    override fun onFailure(call: Call, e: IOException) {

                        Log.e("ERTI", "onFailure $e")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.e("ERTI", "onResponseCheck $response")

                    }
                })
                chain.proceed(newRequest)

            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // 기본 URL을 설정합니다.
            .addConverterFactory(MoshiConverterFactory.create()) // Gson 변환기를 사용합니다.
            .client(okHttp)
            .build()

        val apiService = retrofit.create(ApiService::class.java)

   val api =  runBlocking {  RemoteDataSource(apiService).getLegalQA("", RequestLegalObject("범죄"))}

//
//            val api = runBlocking { apiService.getLegalQa("${BuildConfig.API_KEY}", RequestLegalObject("이민")) }.body()


        assertEquals("", api)
    }

    @Test
    fun `Api Request 및 Response 데이터 확인`() {
        val client = OkHttpClient()
        val resu = TESTRequestLegalQAModel(
            accessKey = "${BuildConfig.API_KEY}",
            argument = TESTRequestLegalObject(
                "이민"
            )
        )
       val apiUrl = "${BuildConfig.BASE_URL}/LegalQA"
        val requestBody =  RequestBody.create("application/json".toMediaTypeOrNull(),
                Json.encodeToString(resu)
            )


        val request = Request.Builder()
            .url(apiUrl)
            .addHeader("Authorization", "${BuildConfig.API_KEY}")
            .post(
                requestBody
            )
            .build()

            val response = client.newCall(request).execute()
            response.runCatching {

            }.onSuccess {
                assertEquals("", response.body?.string())
            }.onFailure {
                Log.e("ResponseError", "$it")
                    assertEquals("error", it)
            }

        Log.e("response", "${response.body?.toString()}")
        }
    }
    @Test
    fun test() {

     assertEquals(1, 1)

    }








@Serializable
data class TESTRequestLegalQAModel(
    val accessKey : String,
    val argument : TESTRequestLegalObject
)
@Serializable
data class TESTRequestLegalObject(
    val question: String
)
open class BaseTest {
    fun mockLogClass() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }
}
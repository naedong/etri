package kr.io.etri.common.exception

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-25(025)
 * Time: 오전 10:46
 */
sealed class Result<T> {

    class Success<T>(val data: T, val code: Int) : Result<T>()

    class Loading<T> : Result<T>()

    class ApiError<T>(val message: String, val code: Int) : Result<T>()

    class NetworkError<T>(val throwable: Throwable) : Result<T>()

    class NullResult<T> : Result<T>()
}

class ResponseCall<T> constructor(
    private val callDelegate: Call<T>
) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) = callDelegate.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.body()?.let {
                when(response.code()) {
                    in 200..299 -> {
                        callback.onResponse(this@ResponseCall, Response.success(Result.Success(it, response.code())))
                    }
                    in 400..409 -> {
                        callback.onResponse(this@ResponseCall, Response.success(Result.ApiError(response.message(), response.code())))
                    }
                }
            }?: callback.onResponse(this@ResponseCall, Response.success(Result.NullResult()))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            callback.onResponse(this@ResponseCall, Response.success(Result.NetworkError(t)))
            call.cancel()
        }
    })

    override fun clone(): Call<Result<T>> = ResponseCall(callDelegate.clone())

    override fun execute(): Response<Result<T>> = throw UnsupportedOperationException("ResponseCall does not support execute.")

    override fun isExecuted(): Boolean = callDelegate.isExecuted

    override fun cancel() = callDelegate.cancel()

    override fun isCanceled(): Boolean = callDelegate.isCanceled

    override fun request(): Request = callDelegate.request()

    override fun timeout(): Timeout = callDelegate.timeout()
}

class ResponseAdapter<T>(
    private val successType : Type
) : CallAdapter<T, Call<Result<T>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<Result<T>> = ResponseCall(call)
}

class ResponseAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit : Retrofit): CallAdapter<*, *>? {

        if (Call::class.java != getRawType(returnType)) return null
        check(returnType is ParameterizedType)

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Result::class.java) return null
        check(responseType is ParameterizedType)


        val successType = getParameterUpperBound(0, responseType)

        return ResponseAdapter<Any>(successType)
    }
}
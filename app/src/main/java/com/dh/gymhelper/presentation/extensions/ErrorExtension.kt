package com.dh.gymhelper.presentation.extensions

import com.dh.gymhelper.domain.error.ErrorModel
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Response<T>.toError(): ErrorModel.Http {
    try {
        return when {
//        code() == 401 -> ErrorModel.Http.Unauthorized
//        code() == 403 -> ErrorModel.Http.Forbidden
//        code() == 404 -> ErrorModel.Http.NotFound
//        code() == 405 -> ErrorModel.Http.MethodNotAllowed
            code() in 500..600 ->
                ErrorModel.Http.ServerError(
                    code(),
                    message(),
                    errorBody()?.string()
                )
            else ->
                ErrorModel.Http.Custom(
                    code(),
                    message(),
                    errorBody()?.string()
                )
        }
    } catch (e: Throwable) {
        return ErrorModel.Http.BadRequest
    }
}

fun Throwable.toError(): ErrorModel {
    return when (this) {
        is SocketTimeoutException -> ErrorModel.Connection.Timeout
        is UnknownHostException -> ErrorModel.Connection.UnknownHost
        is IOException -> ErrorModel.Connection.IOError
        is SerializationException -> ErrorModel.DataError.ParseError(this)
        else -> ErrorModel.Unknown(this)
    }
}
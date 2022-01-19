package com.dh.gymhelper.presentation.extensions

import com.dh.gymhelper.domain.error.ErrorModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

sealed class UseCaseResult<out T> {
    data class Success<T>(val value: T) : UseCaseResult<T>()
    data class Error(val error: ErrorModel) : UseCaseResult<Nothing>()
}

suspend inline fun <T> safeUseCase(
    crossinline block: suspend () -> T
): UseCaseResult<T> = try {
    UseCaseResult.Success(block())
} catch (e: ErrorModel) {
    UseCaseResult.Error(e.toError())
}

inline fun <T> safeFlow(
    crossinline block: suspend () -> T
): Flow<UseCaseResult<T>> = flow {
    try {
        val repoResult = block()
        emit(UseCaseResult.Success(repoResult))
    } catch (e: ErrorModel) {
        emit(UseCaseResult.Error(e))
    } catch (e: Exception) {
        emit(UseCaseResult.Error(e.toError()))
    }
}

@ExperimentalCoroutinesApi
fun <T> observableFlow(block: suspend FlowCollector<T>.() -> Unit): Flow<UseCaseResult<T>> = flow(block)
    .catch { exception ->
        UseCaseResult.Error(exception.toError())
    }
    .map {
        UseCaseResult.Success(it)
    }

@ExperimentalCoroutinesApi
fun <T> Flow<UseCaseResult<T>>.onSuccess(action: suspend (T) -> Unit): Flow<UseCaseResult<T>> = transform { result ->
    if(result is UseCaseResult.Success<T>) {
        action(result.value)
    }
    return@transform emit(result)
}

@ExperimentalCoroutinesApi
fun <T> Flow<UseCaseResult<T>>.onError(action: suspend (ErrorModel) -> Unit): Flow<UseCaseResult<T>> = transform { result ->
    if(result is UseCaseResult.Error) {
        action(result.error)
    }
    return@transform emit(result)
}
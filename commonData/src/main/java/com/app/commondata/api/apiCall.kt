package com.app.commondata.api

import retrofit2.HttpException
import java.io.IOException

suspend fun <T, R> apiCall(
    call: suspend () -> T,
    mapper: suspend (T) -> R
): Result<R> {
    return try {
        val response = call()
        Result.success(mapper(response))
    } catch (e: HttpException) {
        Result.failure(e)
    } catch (e: IOException) {
        Result.failure(e)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
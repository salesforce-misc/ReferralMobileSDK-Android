package com.salesforce.referral.api

import retrofit2.Response
import java.io.IOException

suspend fun <T: Any> safeApiCall(call: suspend () -> Response<T>): com.salesforce.referral.api.ApiResponse<T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            com.salesforce.referral.api.ApiResponse.Success(response.body()!!)
        } else {
            val errorMessage = response.errorBody()?.string()
            com.salesforce.referral.api.ApiResponse.Error(errorMessage)
        }
    } catch (exception: IOException) {
        com.salesforce.referral.api.ApiResponse.NetworkError
    }  catch (exception: Exception) {
        com.salesforce.referral.api.ApiResponse.Error(exception.message)
    }
}
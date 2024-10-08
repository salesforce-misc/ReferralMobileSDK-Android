/*
 * Copyright (c) 2023, Salesforce, Inc.
 * All rights reserved.
 * SPDX-License-Identifier: BSD-3-Clause
 * For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
 */

package com.salesforce.referral.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.salesforce.referral.entities.ApiErrorResponse
import retrofit2.Response
import java.io.IOException

/**
 * This function takes the network response and returns the response based on success or failure states.
 * Wrapping the network request/response in this function helps reducing redundant code.
 *
 * It returns either a successful response containing data of type [T]
 * or an Error response containing an exception/error message.
 * or a NetWork Error if network is not available
 */
suspend fun <T: Any> safeApiCall(call: suspend () -> Response<T>): ApiResponse<T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            ApiResponse.Success(response.body()!!)
        } else {
            val errorMessage = response.errorBody()?.string()
            val type = object: TypeToken<List<ApiErrorResponse>>(){}.type
            val errorMessageBody = Gson().fromJson<List<ApiErrorResponse>>(errorMessage, type)
            ApiResponse.Error(errorMessageBody?.firstOrNull()?.message)
        }
    } catch (exception: IOException) {
        ApiResponse.NetworkError
    }  catch (exception: Exception) {
        ApiResponse.Error(exception.message)
    }
}
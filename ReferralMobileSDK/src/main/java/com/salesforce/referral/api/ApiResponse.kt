package com.salesforce.referral.api

/**
 * API Response is a sealed class representing the result of a network API call.
 * It encapsulates either a successful response containing data of type [T]
 * or an Error response containing an exception/error message.
 * or a NetWork Error if network is not available
 */
sealed interface ApiResponse<out T: Any> {
    /**
     * Represents a successful response from the API containing the data of type [T]
     * @property data - The data retrieved from the API
     * @constructor Creates a success instance with the provided data
     */
    data class Success<out T: Any>(val data: T): ApiResponse<T>

    /**
     * Represents an error response from the API containing error message
     * @property errorMessage - error retrieved from the API
     * @constructor Creates an Error instance with the provided exception
     */
    data class Error(val errorMessage: String?): ApiResponse<Nothing>

    /**
     * Represents a Network error
     */
    object NetworkError: ApiResponse<Nothing>
}
package com.codebaron.kongaposts.remote

/**
 * @author Anyanwu Nicholas(codeBaron)
 * @since 16-11-2023
 */

/**
 * Sealed class for handling different states of a response.
 * @param R The type of data associated with the response.
 */
open class ResponseHandler<out R> {

    /**
     * Object representing the loading state.
     */
    object Loading : ResponseHandler<Nothing>()

    /**
     * Data class representing a successful response.
     * @param T The type of data associated with the successful response.
     * @property data The data associated with the successful response.
     */
    data class Success<out T>(val data: T) : ResponseHandler<T>()

    /**
     * Data class representing an exceptional response with a Kotlin Exception.
     * @property exception The Kotlin Exception associated with the exceptional response.
     */
    data class Exception(val exception: kotlin.Exception) : ResponseHandler<Nothing>()

    /**
     * Data class representing an error response with an optional error message.
     * @param message The optional error message associated with the error response.
     */
    data class Error(val message: String?) : ResponseHandler<Nothing>()

    /**
     * Data class representing an error response with a Throwable.
     * @param throwable The Throwable associated with the error response.
     */
    data class ThrowableError(val throwable: Throwable): ResponseHandler<Nothing>()
}
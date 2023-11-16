package com.codebaron.kongaposts.remote

import java.io.Serializable

data class ErrorModel(
    val message: String? = null,
    val errors: String? = null
): Serializable
package com.johnturkson.aws.lambda.data

import kotlinx.serialization.Serializable

@Serializable
data class HttpResponse(
    val statusCode: Int,
    val headers: Map<String, String>,
    val body: String,
    val isBase64Encoded: Boolean,
)

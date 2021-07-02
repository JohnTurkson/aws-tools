package com.johnturkson.aws.lambda.data.http

import com.johnturkson.aws.lambda.data.LambdaResponse
import kotlinx.serialization.Serializable

@Serializable
data class HttpLambdaResponse(
    override val body: String,
    val statusCode: Int = 200,
    val headers: Map<String, String> = mapOf("content-type" to "application/json"),
    val isBase64Encoded: Boolean = false,
) : LambdaResponse

package com.johnturkson.aws.lambda.data.http

import com.johnturkson.aws.lambda.data.LambdaRequest
import kotlinx.serialization.Serializable

@Serializable
data class HttpLambdaRequest(
    val version: String,
    val routeKey: String,
    val rawPath: String,
    val rawQueryString: String,
    val headers: Map<String, String> = emptyMap(),
    val requestContext: HttpLambdaRequestContext,
    val isBase64Encoded: Boolean,
    override val body: String? = null,
) : LambdaRequest

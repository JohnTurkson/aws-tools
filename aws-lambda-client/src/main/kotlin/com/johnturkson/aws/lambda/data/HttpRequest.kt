package com.johnturkson.aws.lambda.data

import kotlinx.serialization.Serializable

@Serializable
data class HttpRequest(
    val version: String,
    val routeKey: String,
    val rawPath: String,
    val rawQueryString: String,
    val headers: Map<String, String> = emptyMap(),
    val queryStringParameters: Map<String, String> = emptyMap(),
    val requestContext: HttpRequestContext,
    val body: String? = null,
    val isBase64Encoded: Boolean,
)
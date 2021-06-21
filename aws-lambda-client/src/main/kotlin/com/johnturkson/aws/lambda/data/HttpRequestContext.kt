package com.johnturkson.aws.lambda.data

import kotlinx.serialization.Serializable

@Serializable
data class HttpRequestContext(
    val http: HttpRequestContextMetadata,
    val requestId: String,
    val routeKey: String,
    val stage: String,
    val time: String,
)

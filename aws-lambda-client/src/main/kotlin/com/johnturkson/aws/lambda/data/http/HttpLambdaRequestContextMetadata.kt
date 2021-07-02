package com.johnturkson.aws.lambda.data.http

import kotlinx.serialization.Serializable

@Serializable
data class HttpLambdaRequestContextMetadata(
    val method: String,
    val path: String,
    val protocol: String,
    val sourceIp: String,
    val userAgent: String,
)

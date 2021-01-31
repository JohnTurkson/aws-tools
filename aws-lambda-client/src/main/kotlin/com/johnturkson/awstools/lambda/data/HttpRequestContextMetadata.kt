package com.johnturkson.awstools.lambda.data

import kotlinx.serialization.Serializable

@Serializable
data class HttpRequestContextMetadata(
    val method: String,
    val path: String,
    val protocol: String,
    val sourceIp: String,
    val userAgent: String,
)

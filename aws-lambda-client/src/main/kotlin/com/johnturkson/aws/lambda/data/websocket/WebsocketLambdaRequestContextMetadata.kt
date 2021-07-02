package com.johnturkson.aws.lambda.data.websocket

import kotlinx.serialization.Serializable

@Serializable
data class WebsocketLambdaRequestContextMetadata(
    val userAgent: String,
    val sourceIp: String,
)

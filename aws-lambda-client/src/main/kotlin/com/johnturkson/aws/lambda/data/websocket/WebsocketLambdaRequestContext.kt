package com.johnturkson.aws.lambda.data.websocket

import kotlinx.serialization.Serializable

@Serializable
data class WebsocketLambdaRequestContext(
    val routeKey: String,
    val messageId: String,
    val eventType: String,
    val extendedRequestId: String,
    val requestTime: String,
    val messageDirection: String,
    val stage: String,
    val connectedAt: Long,
    val requestTimeEpoch: Long,
    val identity: WebsocketLambdaRequestContextMetadata,
    val requestId: String,
    val domainName: String,
    val connectionId: String,
    val apiId: String,
)

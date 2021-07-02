package com.johnturkson.aws.lambda.data.websocket

import com.johnturkson.aws.lambda.data.LambdaRequest
import kotlinx.serialization.Serializable

@Serializable
data class WebsocketLambdaRequest(
    val requestContext: WebsocketLambdaRequestContext,
    val isBase64Encoded: Boolean,
    override val body: String,
) : LambdaRequest

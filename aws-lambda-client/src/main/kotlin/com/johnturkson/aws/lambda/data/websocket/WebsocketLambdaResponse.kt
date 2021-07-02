package com.johnturkson.aws.lambda.data.websocket

import com.johnturkson.aws.lambda.data.LambdaResponse
import kotlinx.serialization.Serializable

@Serializable
data class WebsocketLambdaResponse(override val body: String) : LambdaResponse

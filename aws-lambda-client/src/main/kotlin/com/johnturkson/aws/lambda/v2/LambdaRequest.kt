package com.johnturkson.aws.lambda.v2

import kotlinx.serialization.Serializable

@Serializable
sealed class LambdaRequest {
    @Serializable
    data class RawLambdaRequest(val body: String) : LambdaRequest()
    
    @Serializable
    data class HttpLambdaRequest(val version: String, val body: String?) : LambdaRequest()
    
    @Serializable
    data class WebsocketLambdaRequest(val body: String) : LambdaRequest()
}

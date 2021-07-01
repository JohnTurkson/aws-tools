package com.johnturkson.aws.lambda.v2

import kotlinx.serialization.Serializable

@Serializable
sealed class LambdaResponse {
    @Serializable
    data class RawLambdaResponse(val body: String) : LambdaResponse()
    
    @Serializable
    data class HttpLambdaResponse(val statusCode: Int, val body: String) : LambdaResponse()
    
    @Serializable
    data class WebsocketLambdaResponse(val body: String) : LambdaResponse()
}

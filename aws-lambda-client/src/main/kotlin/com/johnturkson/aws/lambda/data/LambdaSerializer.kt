package com.johnturkson.aws.lambda.data

import com.johnturkson.aws.lambda.data.http.HttpLambdaRequest
import com.johnturkson.aws.lambda.data.raw.RawLambdaRequest
import com.johnturkson.aws.lambda.data.websocket.WebsocketLambdaRequest
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

object LambdaSerializer : JsonContentPolymorphicSerializer<LambdaRequest>(LambdaRequest::class) {
    private val httpLambdaRequestKeys = setOf(
        "version", "routeKey", "rawPath", "rawQueryString", "requestContext", "isBase64Encoded"
    )
    
    private val websocketLambdaRequestKeys = setOf(
        "requestContext", "body", "isBase64Encoded"
    )
    
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out LambdaRequest> {
        return when {
            element.isHttpLambdaRequest() -> HttpLambdaRequest.serializer()
            element.isWebsocketLambdaRequest() -> WebsocketLambdaRequest.serializer()
            else -> RawLambdaRequest.serializer()
        }
    }
    
    private fun JsonElement.isHttpLambdaRequest(): Boolean {
        if (this !is JsonObject) return false
        return this.keys.containsAll(httpLambdaRequestKeys)
    }
    
    private fun JsonElement.isWebsocketLambdaRequest(): Boolean {
        if (this !is JsonObject) return false
        return this.keys.containsAll(websocketLambdaRequestKeys)
    }
}

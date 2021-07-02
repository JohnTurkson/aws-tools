package com.johnturkson.aws.lambda.data

import com.johnturkson.aws.lambda.data.http.HttpLambdaRequest
import com.johnturkson.aws.lambda.data.raw.RawLambdaRequest
import com.johnturkson.aws.lambda.data.websocket.WebsocketLambdaRequest
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

object LambdaDeserializer : DeserializationStrategy<LambdaRequest> {
    private val httpLambdaRequestKeys = setOf(
        "version", "routeKey", "rawPath", "rawQueryString", "requestContext", "isBase64Encoded"
    )
    
    private val websocketLambdaRequestKeys = setOf(
        "requestContext", "body", "isBase64Encoded"
    )
    
    @InternalSerializationApi
    override val descriptor = buildSerialDescriptor("LambdaRequest", PolymorphicKind.OPEN) {
        element("RawLambdaRequest", String.serializer().descriptor)
        element("HttpLambdaRequest", HttpLambdaRequest.serializer().descriptor)
        element("WebsocketLambdaRequest", WebsocketLambdaRequest.serializer().descriptor)
    }
    
    override fun deserialize(decoder: Decoder): LambdaRequest {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val body = decoder.json.encodeToString(JsonElement.serializer(), element)
        return when {
            element.isHttpLambdaRequest() -> decoder.json.decodeFromJsonElement(
                HttpLambdaRequest.serializer(),
                element
            )
            element.isWebsocketLambdaRequest() -> decoder.json.decodeFromJsonElement(
                WebsocketLambdaRequest.serializer(),
                element
            )
            else -> RawLambdaRequest(body)
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

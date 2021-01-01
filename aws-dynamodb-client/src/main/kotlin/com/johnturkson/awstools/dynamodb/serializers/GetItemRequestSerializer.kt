package com.johnturkson.awstools.dynamodb.serializers

import com.johnturkson.awstools.dynamodb.requests.GetItemRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder

class GetItemRequestSerializer<T>(dataSerializer: KSerializer<T>) : KSerializer<GetItemRequest<T>> {
    override val descriptor: SerialDescriptor = GetItemRequest.serializer(dataSerializer).descriptor
    private val requestSerializer = GetItemRequest.serializer(JsonElement.serializer())
    private val transformingSerializer = TransformingSerializer(dataSerializer)
    
    override fun serialize(encoder: Encoder, value: GetItemRequest<T>) {
        require(encoder is JsonEncoder)
        val key = encoder.json.encodeToJsonElement(transformingSerializer, value.key)
        val request = GetItemRequest(
            tableName = value.tableName,
            key = key,
            expressionAttributeNames = value.expressionAttributeNames,
            projectionExpression = value.projectionExpression,
            consistentRead = value.consistentRead,
        )
        encoder.encodeSerializableValue(requestSerializer, request)
    }
    
    override fun deserialize(decoder: Decoder): GetItemRequest<T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val request = decoder.json.decodeFromJsonElement(requestSerializer, element)
        val key = decoder.json.decodeFromJsonElement(transformingSerializer, request.key)
        return GetItemRequest(
            tableName = request.tableName,
            key = key,
            expressionAttributeNames = request.expressionAttributeNames,
            projectionExpression = request.projectionExpression,
            consistentRead = request.consistentRead
        )
    }
}

package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.requests.DeleteItemRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder

class DeleteItemRequestSerializer<T>(dataSerializer: KSerializer<T>) : KSerializer<DeleteItemRequest<T>> {
    override val descriptor: SerialDescriptor = DeleteItemRequest.serializer(dataSerializer).descriptor
    private val requestSerializer = DeleteItemRequest.serializer(JsonElement.serializer())
    private val transformingSerializer = TransformingSerializer(dataSerializer)
    
    override fun serialize(encoder: Encoder, value: DeleteItemRequest<T>) {
        require(encoder is JsonEncoder)
        val key = encoder.json.encodeToJsonElement(transformingSerializer, value.key)
        val request = DeleteItemRequest(
            tableName = value.tableName,
            key = key,
            returnValues = value.returnValues,
            conditionExpression = value.conditionExpression,
            expressionAttributeNames = value.expressionAttributeNames,
            expressionAttributeValues = value.expressionAttributeValues,
        )
        encoder.encodeSerializableValue(requestSerializer, request)
    }
    
    override fun deserialize(decoder: Decoder): DeleteItemRequest<T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val request = decoder.json.decodeFromJsonElement(requestSerializer, element)
        val key = decoder.json.decodeFromJsonElement(transformingSerializer, request.key)
        return DeleteItemRequest(
            tableName = request.tableName,
            key = key,
            returnValues = request.returnValues,
            conditionExpression = request.conditionExpression,
            expressionAttributeNames = request.expressionAttributeNames,
            expressionAttributeValues = request.expressionAttributeValues,
        )
    }
}

package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.requests.PutItemRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder

class PutItemRequestSerializer<T>(dataSerializer: KSerializer<T>) : KSerializer<PutItemRequest<T>> {
    override val descriptor: SerialDescriptor = PutItemRequest.serializer(dataSerializer).descriptor
    private val requestSerializer = PutItemRequest.serializer(JsonElement.serializer())
    private val transformingSerializer = TransformingSerializer(dataSerializer)
    
    override fun serialize(encoder: Encoder, value: PutItemRequest<T>) {
        require(encoder is JsonEncoder)
        val item = encoder.json.encodeToJsonElement(transformingSerializer, value.item)
        val request = PutItemRequest(
            tableName = value.tableName,
            item = item,
            returnValues = value.returnValues,
            conditionExpression = value.conditionExpression,
            expressionAttributeNames = value.expressionAttributeNames,
            expressionAttributeValues = value.expressionAttributeValues,
        )
        encoder.encodeSerializableValue(requestSerializer, request)
    }
    
    override fun deserialize(decoder: Decoder): PutItemRequest<T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val request = decoder.json.decodeFromJsonElement(requestSerializer, element)
        val item = decoder.json.decodeFromJsonElement(transformingSerializer, request.item)
        return PutItemRequest(
            tableName = request.tableName,
            item = item,
            returnValues = request.returnValues,
            conditionExpression = request.conditionExpression,
            expressionAttributeNames = request.expressionAttributeNames,
            expressionAttributeValues = request.expressionAttributeValues,
        )
    }
}

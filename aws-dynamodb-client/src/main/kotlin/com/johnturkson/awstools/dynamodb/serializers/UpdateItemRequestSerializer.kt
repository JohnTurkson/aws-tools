package com.johnturkson.awstools.dynamodb.serializers

import com.johnturkson.awstools.dynamodb.requests.UpdateItemRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder

class UpdateItemRequestSerializer<T>(dataSerializer: KSerializer<T>) : KSerializer<UpdateItemRequest<T>> {
    override val descriptor: SerialDescriptor = UpdateItemRequest.serializer(dataSerializer).descriptor
    private val requestSerializer = UpdateItemRequest.serializer(JsonElement.serializer())
    private val transformingSerializer = TransformingSerializer(dataSerializer)
    
    override fun serialize(encoder: Encoder, value: UpdateItemRequest<T>) {
        require(encoder is JsonEncoder)
        val key = encoder.json.encodeToJsonElement(transformingSerializer, value.key)
        val request = UpdateItemRequest(
            tableName = value.tableName,
            key = key,
            returnValues = value.returnValues,
            updateExpression = value.updateExpression,
            conditionExpression = value.conditionExpression,
            expressionAttributeNames = value.expressionAttributeNames,
            expressionAttributeValues = value.expressionAttributeValues,
        )
        encoder.encodeSerializableValue(requestSerializer, request)
    }
    
    override fun deserialize(decoder: Decoder): UpdateItemRequest<T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val request = decoder.json.decodeFromJsonElement(requestSerializer, element)
        val key = decoder.json.decodeFromJsonElement(transformingSerializer, request.key)
        return UpdateItemRequest(
            tableName = request.tableName,
            key = key,
            returnValues = request.returnValues,
            updateExpression = request.updateExpression,
            conditionExpression = request.conditionExpression,
            expressionAttributeNames = request.expressionAttributeNames,
            expressionAttributeValues = request.expressionAttributeValues,
        )
    }
}

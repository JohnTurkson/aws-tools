package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.requests.ScanRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder

class ScanRequestSerializer<T>(dataSerializer: KSerializer<T>) : KSerializer<ScanRequest<T>> {
    override val descriptor: SerialDescriptor = ScanRequest.serializer(dataSerializer).descriptor
    private val requestSerializer = ScanRequest.serializer(JsonElement.serializer())
    private val transformingSerializer = TransformingSerializer(dataSerializer)
    
    override fun serialize(encoder: Encoder, value: ScanRequest<T>) {
        require(encoder is JsonEncoder)
        val exclusiveStartKey = value.exclusiveStartKey?.let {
            encoder.json.encodeToJsonElement(transformingSerializer, it)
        }
        val request = ScanRequest(
            tableName = value.tableName,
            limit = value.limit,
            exclusiveStartKey = exclusiveStartKey,
            expressionAttributeNames = value.expressionAttributeNames,
            expressionAttributeValues = value.expressionAttributeValues,
            projectionExpression = value.projectionExpression,
            filterExpression = value.filterExpression,
            consistentRead = value.consistentRead,
        )
        encoder.encodeSerializableValue(requestSerializer, request)
    }
    
    override fun deserialize(decoder: Decoder): ScanRequest<T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val request = decoder.json.decodeFromJsonElement(requestSerializer, element)
        val exclusiveStartKey = request.exclusiveStartKey?.let {
            decoder.json.decodeFromJsonElement(transformingSerializer, it)
        }
        return ScanRequest(
            tableName = request.tableName,
            limit = request.limit,
            exclusiveStartKey = exclusiveStartKey,
            expressionAttributeNames = request.expressionAttributeNames,
            expressionAttributeValues = request.expressionAttributeValues,
            projectionExpression = request.projectionExpression,
            filterExpression = request.filterExpression,
            consistentRead = request.consistentRead,
        )
    }
}

package com.johnturkson.awstools.dynamodb.serializers

import com.johnturkson.awstools.dynamodb.requests.QueryRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder

class QueryRequestSerializer<T>(dataSerializer: KSerializer<T>) : KSerializer<QueryRequest<T>> {
    override val descriptor: SerialDescriptor = QueryRequest.serializer(dataSerializer).descriptor
    private val requestSerializer = QueryRequest.serializer(JsonElement.serializer())
    private val transformingSerializer = TransformingSerializer(dataSerializer)
    
    override fun serialize(encoder: Encoder, value: QueryRequest<T>) {
        require(encoder is JsonEncoder)
        val exclusiveStartKey = value.exclusiveStartKey?.let {
            encoder.json.encodeToJsonElement(transformingSerializer, it)
        }
        val request = QueryRequest(
            tableName = value.tableName,
            indexName = value.indexName,
            limit = value.limit,
            keyConditionExpression = value.keyConditionExpression,
            exclusiveStartKey = exclusiveStartKey,
            expressionAttributeNames = value.expressionAttributeNames,
            expressionAttributeValues = value.expressionAttributeValues,
            projectionExpression = value.projectionExpression,
            filterExpression = value.filterExpression,
            consistentRead = value.consistentRead,
            scanIndexForward = value.scanIndexForward,
        )
        encoder.encodeSerializableValue(requestSerializer, request)
    }
    
    override fun deserialize(decoder: Decoder): QueryRequest<T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val request = decoder.json.decodeFromJsonElement(requestSerializer, element)
        val exclusiveStartKey = request.exclusiveStartKey?.let {
            decoder.json.decodeFromJsonElement(transformingSerializer, it)
        }
        return QueryRequest(
            tableName = request.tableName,
            indexName = request.indexName,
            limit = request.limit,
            keyConditionExpression = request.keyConditionExpression,
            exclusiveStartKey = exclusiveStartKey,
            expressionAttributeNames = request.expressionAttributeNames,
            expressionAttributeValues = request.expressionAttributeValues,
            projectionExpression = request.projectionExpression,
            filterExpression = request.filterExpression,
            consistentRead = request.consistentRead,
            scanIndexForward = request.scanIndexForward,
        )
    }
}

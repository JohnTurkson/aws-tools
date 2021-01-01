package com.johnturkson.awstools.dynamodb.serializers

import com.johnturkson.awstools.dynamodb.responses.QueryResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder

class QueryResponseSerializer<K, T>(
    keySerializer: KSerializer<K>,
    dataSerializer: KSerializer<T>,
) : KSerializer<QueryResponse<K, T>> {
    override val descriptor: SerialDescriptor = QueryResponse.serializer(keySerializer, dataSerializer).descriptor
    private val responseSerializer = QueryResponse.serializer(JsonElement.serializer(), JsonElement.serializer())
    private val keyTransformingSerializer = TransformingSerializer(keySerializer)
    private val dataTransformingSerializer = TransformingSerializer(dataSerializer)
    
    override fun serialize(encoder: Encoder, value: QueryResponse<K, T>) {
        require(encoder is JsonEncoder)
        val items = value.items.map {
            encoder.json.encodeToJsonElement(dataTransformingSerializer, it)
        }
        val lastEvaluatedKey = value.lastEvaluatedKey?.let {
            encoder.json.encodeToJsonElement(keyTransformingSerializer, it)
        }
        val response = QueryResponse(
            count = value.count,
            items = items,
            scannedCount = value.scannedCount,
            lastEvaluatedKey = lastEvaluatedKey,
        )
        encoder.encodeSerializableValue(responseSerializer, response)
    }
    
    override fun deserialize(decoder: Decoder): QueryResponse<K, T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val response = decoder.json.decodeFromJsonElement(responseSerializer, element)
        val items = response.items.map {
            decoder.json.decodeFromJsonElement(dataTransformingSerializer, it)
        }
        val lastEvaluatedKey = response.lastEvaluatedKey?.let {
            decoder.json.decodeFromJsonElement(keyTransformingSerializer, it)
        }
        return QueryResponse(
            count = response.count,
            items = items,
            scannedCount = response.scannedCount,
            lastEvaluatedKey = lastEvaluatedKey,
        )
    }
}

package com.johnturkson.awstools.dynamodb.request.serializers

import com.johnturkson.awstools.dynamodb.objectbuilder.DynamoDBObject
import com.johnturkson.awstools.dynamodb.objectserializer.DynamoDBTransformingSerializer
import com.johnturkson.awstools.dynamodb.request.ScanResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

class ScanResponseSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<ScanResponse<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    
    override fun serialize(encoder: Encoder, value: ScanResponse<T>) {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json = (encoder as JsonEncoder).json
        val items = json.encodeToJsonElement(ListSerializer(transformer), value.items)
        val count = JsonPrimitive(value.count)
        val scannedCount = JsonPrimitive(value.scannedCount)
        val lastEvaluatedKey = json.encodeToJsonElement(DynamoDBObject.serializer(), value.lastEvaluatedKey)
        val response = JsonObject(mapOf(
            "Count" to count,
            "Items" to items,
            "ScannedCount" to scannedCount,
            "LastEvaluatedKey" to lastEvaluatedKey,
        ))
        encoder.encodeJsonElement(response)
    }
    
    override fun deserialize(decoder: Decoder): ScanResponse<T> {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json = (decoder as JsonDecoder).json
        val response = decoder.decodeJsonElement() as JsonObject
        val items = json.decodeFromJsonElement(ListSerializer(transformer), response["Items"]!!)
        val count = response["Count"]!!.jsonPrimitive.int
        val scannedCount = response["ScannedCount"]!!.jsonPrimitive.int
        val lastEvaluatedKey = json.decodeFromJsonElement(
            DynamoDBObject.serializer(),
            response["LastEvaluatedKey"]!!.jsonObject
        )
        return ScanResponse(count, items, scannedCount, lastEvaluatedKey)
    }
}

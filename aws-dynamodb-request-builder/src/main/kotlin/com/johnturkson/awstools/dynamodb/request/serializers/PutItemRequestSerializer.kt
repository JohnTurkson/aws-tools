package com.johnturkson.awstools.dynamodb.request.serializers

import com.johnturkson.awstools.dynamodb.objectserializer.DynamoDBTransformingSerializer
import com.johnturkson.awstools.dynamodb.request.PutItemRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

class PutItemRequestSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<PutItemRequest<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    
    override fun serialize(encoder: Encoder, value: PutItemRequest<T>) {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json =  (encoder as JsonEncoder).json
        val tableName = JsonPrimitive(value.tableName)
        val item = json.encodeToJsonElement(transformer, value.item)
        val request = JsonObject(mapOf("TableName" to tableName, "Item" to item))
        encoder.encodeJsonElement(request)
    }
    
    override fun deserialize(decoder: Decoder): PutItemRequest<T> {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json = (decoder as JsonDecoder).json
        val request = decoder.decodeJsonElement() as JsonObject
        val tableName = request["TableName"]!!.jsonPrimitive.content
        val item = json.decodeFromJsonElement(transformer, request["Item"]!!)
        return PutItemRequest(tableName, item)
    }
}

package com.johnturkson.awstools.dynamodb.request.serializers

import com.johnturkson.awstools.dynamodb.transformingserializer.DynamoDBTransformingSerializer
import com.johnturkson.awstools.dynamodb.request.GetItemResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject

class GetItemResponseSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<GetItemResponse<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    
    override fun serialize(encoder: Encoder, value: GetItemResponse<T>) {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json = (encoder as JsonEncoder).json
        val item = json.encodeToJsonElement(transformer, value.item)
        val response = JsonObject(mapOf("Item" to item))
        encoder.encodeJsonElement(response)
    }
    
    override fun deserialize(decoder: Decoder): GetItemResponse<T> {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json = (decoder as JsonDecoder).json
        val response = decoder.decodeJsonElement() as JsonObject
        val item = json.decodeFromJsonElement(transformer, response["Item"]!!)
        return GetItemResponse(item)
    }
}

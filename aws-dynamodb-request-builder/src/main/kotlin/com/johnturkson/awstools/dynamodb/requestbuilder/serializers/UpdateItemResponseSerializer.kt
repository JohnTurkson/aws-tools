package com.johnturkson.awstools.dynamodb.requestbuilder.serializers

import com.johnturkson.awstools.dynamodb.requestbuilder.responses.UpdateItemResponse
import com.johnturkson.awstools.dynamodb.transformingserializer.DynamoDBTransformingSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject

class UpdateItemResponseSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<UpdateItemResponse<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    
    override fun serialize(encoder: Encoder, value: UpdateItemResponse<T>) {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json = (encoder as JsonEncoder).json
        val item = when (value.item) {
            null -> JsonNull
            else -> json.encodeToJsonElement(transformer, value.item)
        }
        val response = JsonObject(mapOf("Attributes" to item))
        encoder.encodeJsonElement(response)
    }
    
    override fun deserialize(decoder: Decoder): UpdateItemResponse<T> {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json = (decoder as JsonDecoder).json
        val response = decoder.decodeJsonElement() as JsonObject
        val item = when (response["Attributes"]) {
            null -> null
            else -> json.decodeFromJsonElement(transformer, response["Attributes"]!!)
        }
        return UpdateItemResponse(item)
    }
}

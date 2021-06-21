package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.responses.DeleteItemResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder

class DeleteItemResponseSerializer<T>(dataSerializer: KSerializer<T>) : KSerializer<DeleteItemResponse<T>> {
    override val descriptor: SerialDescriptor = DeleteItemResponse.serializer(dataSerializer).descriptor
    private val responseSerializer = DeleteItemResponse.serializer(JsonElement.serializer())
    private val transformingSerializer = TransformingSerializer(dataSerializer)
    
    override fun serialize(encoder: Encoder, value: DeleteItemResponse<T>) {
        require(encoder is JsonEncoder)
        val attributes = value.attributes?.let {
            encoder.json.encodeToJsonElement(transformingSerializer, it)
        }
        val response = DeleteItemResponse(attributes = attributes)
        encoder.encodeSerializableValue(responseSerializer, response)
    }
    
    override fun deserialize(decoder: Decoder): DeleteItemResponse<T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val response = decoder.json.decodeFromJsonElement(responseSerializer, element)
        val attributes = response.attributes?.let {
            decoder.json.decodeFromJsonElement(transformingSerializer, it)
        }
        return DeleteItemResponse(attributes = attributes)
    }
}

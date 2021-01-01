package com.johnturkson.awstools.dynamodb.serializers

import com.johnturkson.awstools.dynamodb.responses.GetItemResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder

class GetItemResponseSerializer<T>(dataSerializer: KSerializer<T>) : KSerializer<GetItemResponse<T>> {
    override val descriptor: SerialDescriptor = GetItemResponse.serializer(dataSerializer).descriptor
    private val responseSerializer = GetItemResponse.serializer(JsonElement.serializer())
    private val transformingSerializer = TransformingSerializer(dataSerializer)
    
    override fun serialize(encoder: Encoder, value: GetItemResponse<T>) {
        require(encoder is JsonEncoder)
        val item = encoder.json.encodeToJsonElement(transformingSerializer, value.item)
        val response = GetItemResponse(item = item)
        encoder.encodeSerializableValue(responseSerializer, response)
    }
    
    override fun deserialize(decoder: Decoder): GetItemResponse<T> {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement()
        val response = decoder.json.decodeFromJsonElement(responseSerializer, element)
        val item = decoder.json.decodeFromJsonElement(transformingSerializer, response.item)
        return GetItemResponse(item = item)
    }
}

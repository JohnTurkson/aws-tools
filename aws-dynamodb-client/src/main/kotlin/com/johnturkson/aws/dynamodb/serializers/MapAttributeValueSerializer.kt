package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.data.MapAttributeValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

class MapAttributeValueSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<MapAttributeValue<T>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("MapAttributeValue") {
        element("M", dataSerializer.descriptor)
    }
    
    override fun serialize(encoder: Encoder, value: MapAttributeValue<T>) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, dataSerializer, value.value)
        }
    }
    
    override fun deserialize(decoder: Decoder): MapAttributeValue<T> {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> MapAttributeValue(decodeSerializableElement(dataSerializer.descriptor, index, dataSerializer))
                else -> throw Exception()
            }
        }
    }
}

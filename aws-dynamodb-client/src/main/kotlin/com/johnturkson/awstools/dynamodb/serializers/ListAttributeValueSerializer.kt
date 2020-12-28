package com.johnturkson.awstools.dynamodb.serializers

import com.johnturkson.awstools.dynamodb.data.ListAttributeValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

class ListAttributeValueSerializer<T>(private val dataSerializer: KSerializer<List<T>>) : KSerializer<ListAttributeValue<T>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ListAttributeValue") {
        element("L", dataSerializer.descriptor)
    }
    
    override fun serialize(encoder: Encoder, value: ListAttributeValue<T>) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(dataSerializer.descriptor, 0, dataSerializer, value.value)
        }
    }
    
    override fun deserialize(decoder: Decoder): ListAttributeValue<T> {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> ListAttributeValue(decodeSerializableElement(dataSerializer.descriptor, index, dataSerializer))
                else -> throw Exception()
            }
        }
    }
}

package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.data.ListAttributeValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

class ListAttributeValueSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<ListAttributeValue<T>> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ListAttributeValue") {
        element("L", dataSerializer.descriptor)
    }
    
    override fun serialize(encoder: Encoder, value: ListAttributeValue<T>) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, ListSerializer(dataSerializer), value.values)
        }
    }
    
    override fun deserialize(decoder: Decoder): ListAttributeValue<T> {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> ListAttributeValue(decodeSerializableElement(descriptor, index, ListSerializer(dataSerializer)))
                else -> throw Exception()
            }
        }
    }
}

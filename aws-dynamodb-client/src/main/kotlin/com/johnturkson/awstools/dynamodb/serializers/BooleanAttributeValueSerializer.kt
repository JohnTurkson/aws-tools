package com.johnturkson.awstools.dynamodb.serializers

import com.johnturkson.awstools.dynamodb.data.BooleanAttributeValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

class BooleanAttributeValueSerializer : KSerializer<BooleanAttributeValue> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("BooleanAttributeValue") {
        element<Boolean>("B")
    }
    
    override fun serialize(encoder: Encoder, value: BooleanAttributeValue) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, Boolean.serializer(), value.value)
        }
    }
    
    override fun deserialize(decoder: Decoder): BooleanAttributeValue {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> BooleanAttributeValue(decodeSerializableElement(
                    Boolean.serializer().descriptor,
                    index,
                    Boolean.serializer()
                ))
                else -> throw Exception()
            }
        }
    }
}

package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.data.IntAttributeValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

class IntAttributeValueSerializer : KSerializer<IntAttributeValue> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("IntAttributeValue") {
        element<String>("N")
    }
    
    override fun serialize(encoder: Encoder, value: IntAttributeValue) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, String.serializer(), value.value.toString())
        }
    }
    
    override fun deserialize(decoder: Decoder): IntAttributeValue {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> IntAttributeValue(decodeSerializableElement(
                    String.serializer().descriptor,
                    index,
                    String.serializer()
                ).toInt())
                else -> throw Exception()
            }
        }
    }
}

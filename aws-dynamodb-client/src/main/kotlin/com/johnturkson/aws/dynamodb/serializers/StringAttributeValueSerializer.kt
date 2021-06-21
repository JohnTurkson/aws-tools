package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.data.StringAttributeValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

class StringAttributeValueSerializer : KSerializer<StringAttributeValue> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("StringAttributeValue") {
        element<String>("S")
    }
    
    override fun serialize(encoder: Encoder, value: StringAttributeValue) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, String.serializer(), value.value)
        }
    }
    
    override fun deserialize(decoder: Decoder): StringAttributeValue {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> StringAttributeValue(decodeSerializableElement(
                    String.serializer().descriptor,
                    index,
                    String.serializer()
                ))
                else -> throw Exception()
            }
        }
    }
}

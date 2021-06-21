package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.data.StringSetAttributeValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

class StringSetAttributeValueSerializer : KSerializer<StringSetAttributeValue> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("StringSetAttributeValue") {
        element<Set<String>>("SS")
    }
    
    override fun serialize(encoder: Encoder, value: StringSetAttributeValue) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(
                descriptor,
                0,
                SetSerializer(String.serializer()),
                value.values
            )
        }
    }
    
    override fun deserialize(decoder: Decoder): StringSetAttributeValue {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> StringSetAttributeValue(decodeSerializableElement(
                    descriptor,
                    index,
                    SetSerializer(String.serializer())
                ))
                else -> throw Exception()
            }
        }
    }
}

package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.data.IntSetAttributeValue
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

class IntSetAttributeValueSerializer : KSerializer<IntSetAttributeValue> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("IntSetAttributeValue") {
        element<Set<String>>("NS")
    }
    
    override fun serialize(encoder: Encoder, value: IntSetAttributeValue) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(
                SetSerializer(String.serializer()).descriptor,
                0,
                SetSerializer(String.serializer()),
                value.values.map { it.toString() }.toSet()
            )
        }
    }
    
    override fun deserialize(decoder: Decoder): IntSetAttributeValue {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> IntSetAttributeValue(decodeSerializableElement(
                    SetSerializer(String.serializer()).descriptor,
                    index,
                    SetSerializer(String.serializer())
                ).map { it.toInt() }.toSet())
                else -> throw Exception()
            }
        }
    }
}

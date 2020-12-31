package com.johnturkson.awstools.dynamodb.serializers

import com.johnturkson.awstools.dynamodb.data.DoubleSetAttributeValue
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

class DoubleSetAttributeValueSerializer : KSerializer<DoubleSetAttributeValue> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("DoubleSetAttributeValue") {
        element<Set<String>>("NS")
    }
    
    override fun serialize(encoder: Encoder, value: DoubleSetAttributeValue) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(
                SetSerializer(String.serializer()).descriptor,
                0,
                SetSerializer(String.serializer()),
                value.values.map { it.toString() }.toSet()
            )
        }
    }
    
    override fun deserialize(decoder: Decoder): DoubleSetAttributeValue {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> DoubleSetAttributeValue(decodeSerializableElement(
                    SetSerializer(String.serializer()).descriptor,
                    index,
                    SetSerializer(String.serializer())
                ).map { it.toDouble() }.toSet())
                else -> throw Exception()
            }
        }
    }
}

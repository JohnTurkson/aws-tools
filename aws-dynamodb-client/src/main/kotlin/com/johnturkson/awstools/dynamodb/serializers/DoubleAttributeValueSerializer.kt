package com.johnturkson.awstools.dynamodb.serializers

import com.johnturkson.awstools.dynamodb.data.DoubleAttributeValue
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

class DoubleAttributeValueSerializer : KSerializer<DoubleAttributeValue> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("DoubleAttributeValue") {
        element<String>("N")
    }
    
    override fun serialize(encoder: Encoder, value: DoubleAttributeValue) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, String.serializer(), value.value.toString())
        }
    }
    
    override fun deserialize(decoder: Decoder): DoubleAttributeValue {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> DoubleAttributeValue(decodeSerializableElement(
                    String.serializer().descriptor,
                    index,
                    String.serializer()
                ).toDouble())
                else -> throw Exception()
            }
        }
    }
}

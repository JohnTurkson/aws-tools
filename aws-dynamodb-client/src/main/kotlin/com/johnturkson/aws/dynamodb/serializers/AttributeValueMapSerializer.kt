package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.data.AttributeValueMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement

class AttributeValueMapSerializer : KSerializer<AttributeValueMap> {
    private val serializer = MapSerializer(String.serializer(), JsonElement.serializer())
    override val descriptor: SerialDescriptor = serializer.descriptor
    
    override fun serialize(encoder: Encoder, value: AttributeValueMap) {
        encoder.encodeSerializableValue(serializer, value.values)
    }
    
    override fun deserialize(decoder: Decoder): AttributeValueMap {
        return AttributeValueMap(decoder.decodeSerializableValue(serializer))
    }
}

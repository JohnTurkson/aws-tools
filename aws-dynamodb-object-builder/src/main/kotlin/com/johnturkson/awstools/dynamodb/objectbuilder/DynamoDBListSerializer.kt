package com.johnturkson.awstools.dynamodb.objectbuilder

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

internal object DynamoDBListSerializer : KSerializer<DynamoDBList> {
    override val descriptor: SerialDescriptor = JsonObject.serializer().descriptor
    
    override fun serialize(encoder: Encoder, value: DynamoDBList) {
        encoder.encodeSerializableValue(JsonObject.serializer(), JsonObject(mapOf("L" to value.value)))
    }
    
    override fun deserialize(decoder: Decoder): DynamoDBList {
        return DynamoDBList(decoder.decodeSerializableValue(JsonObject.serializer())["L"] as JsonArray)
    }
}

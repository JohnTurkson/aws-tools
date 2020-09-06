package com.johnturkson.awstools.dynamodb.objectbuilder

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonObject

internal object DynamoDBObjectSerializer : KSerializer<DynamoDBObject> {
    override val descriptor: SerialDescriptor = JsonObject.serializer().descriptor
    
    override fun serialize(encoder: Encoder, value: DynamoDBObject) {
        encoder.encodeSerializableValue(JsonObject.serializer(), value.value)
    }
    
    override fun deserialize(decoder: Decoder): DynamoDBObject {
        return DynamoDBObject(decoder.decodeSerializableValue(JsonObject.serializer()))
    }
}

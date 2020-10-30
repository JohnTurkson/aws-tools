package com.johnturkson.awstools.dynamodb.transformingserializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

class DynamoDBTransformingSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<T> {
    private val NULL = "NULL"
    private val STRING = "S"
    private val BOOLEAN = "BOOL"
    private val NUMBER = "N"
    private val LIST = "L"
    private val MAP = "M"
    private val types = listOf(NULL, STRING, BOOLEAN, NUMBER, LIST)
    
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    
    override fun serialize(encoder: Encoder, value: T) {
        val json = (encoder as JsonEncoder).json.encodeToJsonElement(dataSerializer, value)
        val encoded = json.encoded()
        encoder.encodeSerializableValue(JsonElement.serializer(), encoded)
    }
    
    override fun deserialize(decoder: Decoder): T {
        val json = decoder.decodeSerializableValue(JsonObject.serializer())
        val decoded = json.decoded()
        return (decoder as JsonDecoder).json.decodeFromJsonElement(dataSerializer, decoded)
    }
    
    private fun JsonElement.encoded(): JsonElement {
        return when (this) {
            is JsonObject -> this.encoded()
            is JsonArray -> this.encoded()
            is JsonPrimitive -> this.encoded()
        }
    }
    
    private fun JsonElement.encoded(name: String): JsonElement {
        return when (this) {
            is JsonObject -> this.encoded(name)
            is JsonArray -> this.encoded()
            is JsonPrimitive -> this.encoded()
        }
    }
    
    private fun JsonObject.encoded(): JsonObject {
        return JsonObject(this.mapValues { (name, value) -> value.encoded(name) })
    }
    
    private fun JsonObject.encoded(name: String): JsonObject {
        return if (name in types) {
            this
        } else {
            JsonObject(mapOf(MAP to JsonObject(this.mapValues { (name, value) -> value.encoded(name) })))
        }
    }
    
    private fun JsonArray.encoded(): JsonObject {
        return JsonObject(mapOf(LIST to JsonArray(this.map { element -> element.encoded() })))
    }
    
    private fun JsonPrimitive.encoded(): JsonObject {
        return if (this.isString) {
            JsonObject(mapOf(STRING to JsonPrimitive(content)))
        } else {
            this.booleanOrNull?.let { boolean -> JsonObject(mapOf(BOOLEAN to JsonPrimitive(boolean))) }
                ?: this.intOrNull?.let { int -> JsonObject(mapOf(NUMBER to JsonPrimitive(int.toString()))) }
                ?: this.longOrNull?.let { long -> JsonObject(mapOf(NUMBER to JsonPrimitive(long.toString()))) }
                ?: this.floatOrNull?.let { float -> JsonObject(mapOf(NUMBER to JsonPrimitive(float.toString()))) }
                ?: this.doubleOrNull?.let { double -> JsonObject(mapOf(NUMBER to JsonPrimitive(double.toString()))) }
                ?: JsonObject(mapOf(NULL to JsonPrimitive(true)))
        }
    }
    
    private fun JsonElement.decoded(): JsonElement {
        return when (this) {
            is JsonObject -> this.decoded()
            is JsonArray -> throw Exception("Unsupported top level document type")
            is JsonPrimitive -> throw Exception("Unsupported top level scalar type")
        }
    }
    
    private fun JsonObject.decoded(): JsonElement {
        return when (this.entries.singleOrNull()) {
            null -> JsonObject(this.mapValues { (_, value) -> value.decoded() })
            else -> {
                val (key, value) = this.entries.single()
                when (value) {
                    is JsonPrimitive -> value.decoded(key)
                    is JsonArray -> value.decoded()
                    is JsonObject -> value.decoded(key)
                }
            }
        }
    }
    
    private fun JsonObject.decoded(key: String): JsonElement {
        return when (key) {
            LIST -> this.decoded()
            MAP -> this.decoded()
            else -> JsonObject(mapOf(key to this.decoded()))
        }
    }
    
    private fun JsonArray.decoded(): JsonArray {
        return JsonArray(this.map { element -> element.decoded() })
    }
    
    private fun JsonPrimitive.decoded(key: String): JsonPrimitive {
        return when (key) {
            NULL -> JsonNull
            STRING -> JsonPrimitive(this.content)
            BOOLEAN -> this.booleanOrNull?.let { boolean -> JsonPrimitive(boolean) }
                ?: throw Exception("Unable to parse boolean type")
            NUMBER -> this.intOrNull?.let { int -> JsonPrimitive(int) }
                ?: this.longOrNull?.let { long -> JsonPrimitive(long) }
                ?: this.floatOrNull?.let { float -> JsonPrimitive(float) }
                ?: this.doubleOrNull?.let { double -> JsonPrimitive(double) }
                ?: throw Exception("Unable to parse number type")
            else -> throw Exception("Unsupported data type")
        }
    }
}

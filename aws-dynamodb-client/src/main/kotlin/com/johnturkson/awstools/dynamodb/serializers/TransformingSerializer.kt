package com.johnturkson.awstools.dynamodb.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

class TransformingSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<T> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    
    override fun serialize(encoder: Encoder, value: T) {
        require(encoder is JsonEncoder)
        val element = encoder.json.encodeToJsonElement(dataSerializer, value)
        val wrapped = wrap(element)
        encoder.encodeJsonElement(wrapped)
    }
    
    override fun deserialize(decoder: Decoder): T {
        require(decoder is JsonDecoder)
        val element = decoder.decodeJsonElement().jsonObject
        val unwrapped = unwrap(element)
        return decoder.json.decodeFromJsonElement(dataSerializer, unwrapped)
    }
    
    private fun wrap(element: JsonElement): JsonElement {
        return when (element) {
            is JsonPrimitive -> throw Exception("Cannot wrap top-level primitive type")
            is JsonObject -> wrapObject(element)
            is JsonArray -> throw Exception("Cannot wrap top-level array type")
        }
    }
    
    private fun wrapPrimitive(value: JsonPrimitive): JsonElement {
        return when {
            value.booleanOrNull != null -> JsonObject(mapOf("BOOL" to JsonPrimitive(value.boolean)))
            value.intOrNull != null -> JsonObject(mapOf("N" to JsonPrimitive(value.int.toString())))
            value.doubleOrNull != null -> JsonObject(mapOf("N" to JsonPrimitive(value.double.toString())))
            // TODO other types
            else -> JsonObject(mapOf("S" to JsonPrimitive(value.content)))
        }
    }
    
    private fun wrapObject(value: JsonObject): JsonElement {
        return JsonObject(value.mapValues { (_, v) ->
            when (v) {
                is JsonPrimitive -> wrapPrimitive(v)
                is JsonObject -> JsonObject(mapOf("M" to wrapObject(v)))
                is JsonArray -> wrapArray(v)
            }
        })
    }
    
    private fun wrapArray(value: JsonArray): JsonElement {
        if (value.isEmpty()) {
            return JsonObject(mapOf("L" to JsonArray(emptyList())))
        }
        
        if (value.all { e -> e is JsonPrimitive && e.isString }) {
            return JsonObject(mapOf("SS" to value))
        }
        
        if (value.all { e -> e is JsonPrimitive && e.intOrNull != null }) {
            return JsonObject(mapOf("NS" to value))
        }
        
        if (value.all { e -> e is JsonPrimitive && e.doubleOrNull != null }) {
            return JsonObject(mapOf("NS" to value))
        }
        
        return JsonObject(mapOf("L" to JsonArray(value.map { e -> wrap(e) })))
    }
    
    private fun unwrap(element: JsonElement): JsonElement {
        return when (element) {
            is JsonPrimitive -> throw Exception("Cannot unwrap top-level primitive type")
            is JsonObject -> unwrapObject(element)
            is JsonArray -> throw Exception("Cannot unwrap top-level array type")
        }
    }
    
    private fun unwrapObject(value: JsonObject): JsonElement {
        if (value.keys.size == 1) {
            val (k, v) = value.entries.single()
            return when (k) {
                "BOOL" -> JsonPrimitive(v.jsonPrimitive.boolean)
                "S" -> JsonPrimitive(v.jsonPrimitive.content)
                "N" -> JsonPrimitive(
                    v.jsonPrimitive.intOrNull
                        ?: v.jsonPrimitive.doubleOrNull
                        ?: throw Exception("Invalid contents for key: N")
                )
                "L" -> unwrapArray(v.jsonArray, "L")
                "NS" -> unwrapArray(v.jsonArray, "NS")
                "SS" -> unwrapArray(v.jsonArray, "SS")
                "M" -> unwrapObject(v.jsonObject)
                else -> JsonObject(mapOf(k to unwrap(v)))
            }
        } else {
            val values = value.mapValues { (_, v) -> unwrap(v) }
            return JsonObject(values)
        }
    }
    
    private fun unwrapArray(value: JsonArray, type: String): JsonElement {
        return when (type) {
            "L" -> JsonArray(value.map { v -> unwrap(v) })
            "NS" -> JsonArray(value.map { v ->
                JsonPrimitive(
                    v.jsonPrimitive.intOrNull
                        ?: v.jsonPrimitive.doubleOrNull
                        ?: throw Exception("Invalid contents for key: NS")
                )
            })
            "SS" -> value
            else -> throw Exception("Unsupported type: $type")
        }
    }
}

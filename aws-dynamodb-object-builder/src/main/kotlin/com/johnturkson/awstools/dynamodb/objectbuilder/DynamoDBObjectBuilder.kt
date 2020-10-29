package com.johnturkson.awstools.dynamodb.objectbuilder

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

class DynamoDBObjectBuilder {
    private val attributes = mutableMapOf<String, JsonObject>()
    
    fun put(name: String, value: String): DynamoDBObjectBuilder {
        attributes += name to JsonObject(mapOf("S" to JsonPrimitive(value)))
        return this
    }
    
    fun put(name: String, value: Boolean): DynamoDBObjectBuilder {
        attributes += name to JsonObject(mapOf("BOOL" to JsonPrimitive(value)))
        return this
    }
    
    fun put(name: String, value: Number): DynamoDBObjectBuilder {
        attributes += name to JsonObject(mapOf("N" to JsonPrimitive(value)))
        return this
    }
    
    fun put(name: String, value: Nothing?): DynamoDBObjectBuilder {
        attributes += name to JsonObject(mapOf("NULL" to JsonPrimitive(true)))
        return this
    }
    
    fun put(name: String, value: DynamoDBList): DynamoDBObjectBuilder {
        attributes += name to JsonObject(mapOf("L" to value.value))
        return this
    }
    
    fun put(name: String, value: DynamoDBObject): DynamoDBObjectBuilder {
        attributes += name to JsonObject(mapOf("M" to value.value))
        return this
    }
    
    fun build(): DynamoDBObject {
        return DynamoDBObject(buildJsonObject {
            attributes.forEach { (name, value) -> put(name, value) }
        })
    }
}

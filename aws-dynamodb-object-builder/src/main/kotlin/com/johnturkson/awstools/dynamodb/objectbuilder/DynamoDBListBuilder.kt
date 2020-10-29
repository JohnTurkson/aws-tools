package com.johnturkson.awstools.dynamodb.objectbuilder

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray

class DynamoDBListBuilder {
    private val elements = mutableListOf<JsonObject>()
    
    fun add(value: String): DynamoDBListBuilder {
        elements += JsonObject(mapOf("S" to JsonPrimitive(value)))
        return this
    }
    
    fun add(value: Boolean): DynamoDBListBuilder {
        elements += JsonObject(mapOf("BOOL" to JsonPrimitive(value)))
        return this
    }
    
    fun add(value: Number): DynamoDBListBuilder {
        elements += JsonObject(mapOf("N" to JsonPrimitive(value)))
        return this
    }
    
    fun add(value: Nothing?): DynamoDBListBuilder {
        elements += JsonObject(mapOf("NULL" to JsonPrimitive(true)))
        return this
    }
    
    fun add(value: DynamoDBList): DynamoDBListBuilder {
        elements += JsonObject(mapOf("L" to value.value))
        return this
    }
    
    fun add(value: DynamoDBObject): DynamoDBListBuilder {
        elements += JsonObject(mapOf("M" to value.value))
        return this
    }
    
    fun build(): DynamoDBList {
        return DynamoDBList(buildJsonArray {
            elements.forEach { value -> add(value) }
        })
    }
}


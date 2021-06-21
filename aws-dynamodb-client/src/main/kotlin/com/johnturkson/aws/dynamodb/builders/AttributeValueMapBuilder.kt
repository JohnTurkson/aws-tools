package com.johnturkson.aws.dynamodb.builders

import com.johnturkson.aws.dynamodb.data.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

class AttributeValueMapBuilder(val serializer: Json) {
    @PublishedApi
    internal val attributeValues = mutableMapOf<String, JsonElement>()
    
    fun put(name: String, value: Double): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), DoubleAttributeValue(value))
        return this
    }
    
    fun put(name: String, value: DoubleAttributeValue): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), value)
        return this
    }
    
    @JvmName("putDoubles")
    fun put(name: String, values: Set<Double>): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), DoubleSetAttributeValue(values))
        return this
    }
    
    fun put(name: String, vararg values: Double): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), DoubleSetAttributeValue(*values))
        return this
    }
    
    fun put(name: String, values: DoubleSetAttributeValue): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), values)
        return this
    }
    
    fun put(name: String, value: Int): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), IntAttributeValue(value))
        return this
    }
    
    fun put(name: String, value: IntAttributeValue): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), value)
        return this
    }
    
    @JvmName("putInts")
    fun put(name: String, values: Set<Int>): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), IntSetAttributeValue(values))
        return this
    }
    
    fun put(name: String, vararg values: Int): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), IntSetAttributeValue(*values))
        return this
    }
    
    fun put(name: String, values: IntSetAttributeValue): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), values)
        return this
    }
    
    inline fun <reified T> put(name: String, values: List<T>): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), ListAttributeValue(values))
        return this
    }
    
    inline fun <reified T> put(name: String, vararg values: T): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), ListAttributeValue(values))
        return this
    }
    
    inline fun <reified T> put(name: String, values: ListAttributeValue<T>): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), values)
        return this
    }
    
    inline fun <reified T> put(name: String, value: T): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), MapAttributeValue(value))
        return this
    }
    
    inline fun <reified T> put(name: String, value: MapAttributeValue<T>): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), value)
        return this
    }
    
    fun put(name: String, value: String): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), StringAttributeValue(value))
        return this
    }
    
    fun put(name: String, value: StringAttributeValue): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), value)
        return this
    }
    
    @JvmName("putStrings")
    fun put(name: String, values: Set<String>): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), StringSetAttributeValue(values))
        return this
    }
    
    fun put(name: String, vararg values: String): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), StringSetAttributeValue(*values))
        return this
    }
    
    fun put(name: String, values: StringSetAttributeValue): AttributeValueMapBuilder {
        attributeValues += name to serializer.encodeToJsonElement(serializer(), values)
        return this
    }
    
    fun build(): AttributeValueMap {
        return AttributeValueMap(attributeValues)
    }
}

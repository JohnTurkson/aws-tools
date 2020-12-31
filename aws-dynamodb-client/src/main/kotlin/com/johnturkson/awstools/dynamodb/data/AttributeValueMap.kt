package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.AttributeValueMapSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable(AttributeValueMapSerializer::class)
data class AttributeValueMap(internal val values: Map<String, JsonElement>)

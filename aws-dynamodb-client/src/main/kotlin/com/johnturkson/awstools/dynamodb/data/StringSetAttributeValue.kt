package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.StringSetAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(StringSetAttributeValueSerializer::class)
data class StringSetAttributeValue(@SerialName("SS") val values: Set<String>) {
    constructor(vararg values: String): this(values.toSet())
}

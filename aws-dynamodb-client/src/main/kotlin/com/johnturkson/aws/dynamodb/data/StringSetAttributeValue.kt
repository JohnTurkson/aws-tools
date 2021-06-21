package com.johnturkson.aws.dynamodb.data

import com.johnturkson.aws.dynamodb.serializers.StringSetAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(StringSetAttributeValueSerializer::class)
data class StringSetAttributeValue(@SerialName("SS") val values: Set<String>) {
    constructor(vararg values: String): this(values.toSet())
}

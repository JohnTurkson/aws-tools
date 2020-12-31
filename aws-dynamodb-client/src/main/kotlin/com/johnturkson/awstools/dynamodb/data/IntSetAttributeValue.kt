package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.IntSetAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(IntSetAttributeValueSerializer::class)
data class IntSetAttributeValue(@SerialName("NS") val values: Set<Int>) {
    constructor(vararg values: Int): this(values.toSet())
}

package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.DoubleSetAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(DoubleSetAttributeValueSerializer::class)
data class DoubleSetAttributeValue(@SerialName("NS") val values: Set<Double>) {
    constructor(vararg values: Double): this(values.toSet())
}

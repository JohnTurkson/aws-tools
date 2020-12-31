package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.ListAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(ListAttributeValueSerializer::class)
data class ListAttributeValue<T>(@SerialName("L") val values: List<T>) {
    constructor(vararg values: T): this(values.toList())
}

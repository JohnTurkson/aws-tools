package com.johnturkson.aws.dynamodb.data

import com.johnturkson.aws.dynamodb.serializers.DoubleAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(DoubleAttributeValueSerializer::class)
data class DoubleAttributeValue(@SerialName("N") val value: Double)

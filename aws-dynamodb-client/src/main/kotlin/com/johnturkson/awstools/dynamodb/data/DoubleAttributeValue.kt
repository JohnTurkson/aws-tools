package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.DoubleAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(DoubleAttributeValueSerializer::class)
data class DoubleAttributeValue(@SerialName("N") val value: Double)

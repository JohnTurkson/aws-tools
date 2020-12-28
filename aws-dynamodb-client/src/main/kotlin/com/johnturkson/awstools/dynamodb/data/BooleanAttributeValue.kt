package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.BooleanAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(BooleanAttributeValueSerializer::class)
data class BooleanAttributeValue(@SerialName("B") val value: Boolean)

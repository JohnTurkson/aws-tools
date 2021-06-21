package com.johnturkson.aws.dynamodb.data

import com.johnturkson.aws.dynamodb.serializers.BooleanAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(BooleanAttributeValueSerializer::class)
data class BooleanAttributeValue(@SerialName("BOOL") val value: Boolean)

package com.johnturkson.aws.dynamodb.data

import com.johnturkson.aws.dynamodb.serializers.IntAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(IntAttributeValueSerializer::class)
data class IntAttributeValue(@SerialName("N") val value: Int)

package com.johnturkson.aws.dynamodb.data

import com.johnturkson.aws.dynamodb.serializers.StringAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(StringAttributeValueSerializer::class)
data class StringAttributeValue(@SerialName("S") val value: String)

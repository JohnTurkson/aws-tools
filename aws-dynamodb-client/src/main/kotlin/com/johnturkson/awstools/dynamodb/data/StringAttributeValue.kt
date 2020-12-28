package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.StringAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(StringAttributeValueSerializer::class)
data class StringAttributeValue(@SerialName("S") val value: String)

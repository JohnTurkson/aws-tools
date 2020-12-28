package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.DoubleSetAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(DoubleSetAttributeValueSerializer::class)
class DoubleSetAttributeValue(@SerialName("NS") val value: Set<Double>)

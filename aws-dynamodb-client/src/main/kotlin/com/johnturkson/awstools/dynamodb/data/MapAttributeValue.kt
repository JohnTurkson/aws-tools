package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.MapAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(MapAttributeValueSerializer::class)
data class MapAttributeValue<T>(@SerialName("M") val value: T)

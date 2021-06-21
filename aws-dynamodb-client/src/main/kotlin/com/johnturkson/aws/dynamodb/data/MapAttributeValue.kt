package com.johnturkson.aws.dynamodb.data

import com.johnturkson.aws.dynamodb.serializers.MapAttributeValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(MapAttributeValueSerializer::class)
data class MapAttributeValue<T>(@SerialName("M") val value: T)

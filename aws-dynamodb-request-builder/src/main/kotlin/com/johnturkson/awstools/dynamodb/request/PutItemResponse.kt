package com.johnturkson.awstools.dynamodb.request

import com.johnturkson.awstools.dynamodb.request.serializers.PutItemResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = PutItemResponseSerializer::class)
data class PutItemResponse<T>(
    @SerialName("Attributes")
    val item: T? = null,
)

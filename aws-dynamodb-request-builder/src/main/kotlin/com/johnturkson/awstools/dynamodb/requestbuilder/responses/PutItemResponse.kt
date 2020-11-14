package com.johnturkson.awstools.dynamodb.requestbuilder.responses

import com.johnturkson.awstools.dynamodb.requestbuilder.serializers.PutItemResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = PutItemResponseSerializer::class)
data class PutItemResponse<T>(
    @SerialName("Attributes")
    val item: T? = null,
)

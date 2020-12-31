package com.johnturkson.awstools.dynamodb.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PutItemResponse<T>(
    @SerialName("Attributes")
    val item: T? = null,
)

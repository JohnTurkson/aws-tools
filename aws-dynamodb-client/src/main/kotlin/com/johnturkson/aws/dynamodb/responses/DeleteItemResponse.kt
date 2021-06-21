package com.johnturkson.aws.dynamodb.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteItemResponse<T>(
    @SerialName("Attributes")
    val attributes: T? = null,
)

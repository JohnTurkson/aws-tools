package com.johnturkson.awstools.dynamodb.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetItemResponse<T>(
    @SerialName("Item")
    val item: T,
)

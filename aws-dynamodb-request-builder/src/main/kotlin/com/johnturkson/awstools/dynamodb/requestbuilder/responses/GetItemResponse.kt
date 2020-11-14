package com.johnturkson.awstools.dynamodb.requestbuilder.responses

import com.johnturkson.awstools.dynamodb.requestbuilder.serializers.GetItemResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = GetItemResponseSerializer::class)
data class GetItemResponse<T>(
    @SerialName("Item")
    val item: T,
)

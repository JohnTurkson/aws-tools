package com.johnturkson.awstools.dynamodb.request

import com.johnturkson.awstools.dynamodb.request.serializers.DeleteItemResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = DeleteItemResponseSerializer::class)
data class DeleteItemResponse<T>(
    @SerialName("Attributes")
    val item: T? = null,
)

package com.johnturkson.awstools.dynamodb.requestbuilder.responses

import com.johnturkson.awstools.dynamodb.requestbuilder.serializers.DeleteItemResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = DeleteItemResponseSerializer::class)
data class DeleteItemResponse<T>(
    @SerialName("Attributes")
    val item: T? = null,
)

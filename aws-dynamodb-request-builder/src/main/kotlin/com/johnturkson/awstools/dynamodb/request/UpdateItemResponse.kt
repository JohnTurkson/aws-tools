package com.johnturkson.awstools.dynamodb.request

import com.johnturkson.awstools.dynamodb.request.serializers.UpdateItemResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = UpdateItemResponseSerializer::class)
data class UpdateItemResponse<T>(
    @SerialName("Attributes")
    val item: T? = null,
)

package com.johnturkson.awstools.dynamodb.requestbuilder.responses

import com.johnturkson.awstools.dynamodb.requestbuilder.serializers.UpdateItemResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = UpdateItemResponseSerializer::class)
data class UpdateItemResponse<T>(
    @SerialName("Attributes")
    val item: T? = null,
)

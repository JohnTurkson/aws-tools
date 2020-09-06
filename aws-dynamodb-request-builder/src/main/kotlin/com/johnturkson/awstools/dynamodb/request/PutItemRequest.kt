package com.johnturkson.awstools.dynamodb.request

import com.johnturkson.awstools.dynamodb.request.serializers.PutItemRequestSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = PutItemRequestSerializer::class)
data class PutItemRequest<T>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Item")
    val item: T,
)

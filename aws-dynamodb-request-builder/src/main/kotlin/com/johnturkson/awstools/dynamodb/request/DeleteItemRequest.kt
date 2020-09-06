package com.johnturkson.awstools.dynamodb.request

import com.johnturkson.awstools.dynamodb.objectbuilder.DynamoDBObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteItemRequest<T>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Key")
    val key: DynamoDBObject,
)

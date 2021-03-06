package com.johnturkson.aws.dynamodb.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetItemRequest<K>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Key")
    val key: K,
    @SerialName("ExpressionAttributeNames")
    val expressionAttributeNames: Map<String, String>? = null,
    @SerialName("ProjectionExpression")
    val projectionExpression: String? = null,
    @SerialName("ConsistentRead")
    val consistentRead: Boolean? = null,
)

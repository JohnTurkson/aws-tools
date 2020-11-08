package com.johnturkson.awstools.dynamodb.request

import com.johnturkson.awstools.dynamodb.request.components.ProjectionExpression
import com.johnturkson.awstools.dynamodb.request.serializers.GetItemRequestSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = GetItemRequestSerializer::class)
data class GetItemRequest<T>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Key")
    val key: T,
    @SerialName("ExpressionAttributeNames")
    val expressionAttributeNames: Map<String, String>? = null,
    @SerialName("ProjectionExpression")
    val projectionExpression: ProjectionExpression? = null,
    @SerialName("ConsistentRead")
    val consistentRead: Boolean? = null,
)

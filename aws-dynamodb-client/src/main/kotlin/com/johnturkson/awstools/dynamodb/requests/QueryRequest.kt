package com.johnturkson.awstools.dynamodb.requests

import com.johnturkson.awstools.dynamodb.data.ProjectionExpression
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueryRequest<K>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("IndexName")
    val indexName: String? = null,
    @SerialName("Limit")
    val limit: Int? = null,
    @SerialName("KeyConditionExpression")
    val keyConditionExpression: String,
    @SerialName("ExclusiveStartKey")
    val exclusiveStartKey: K? = null,
    @SerialName("ExpressionAttributeNames")
    val expressionAttributeNames: Map<String, String>? = null,
    @SerialName("ExpressionAttributeValues")
    val expressionAttributeValues: K? = null,
    @SerialName("ProjectionExpression")
    val projectionExpression: ProjectionExpression? = null,
    @SerialName("FilterExpression")
    val filterExpression: String? = null,
    @SerialName("ConsistentRead")
    val consistentRead: Boolean? = null,
    @SerialName("ScanIndexForward")
    val scanIndexForward: Boolean? = null,
)

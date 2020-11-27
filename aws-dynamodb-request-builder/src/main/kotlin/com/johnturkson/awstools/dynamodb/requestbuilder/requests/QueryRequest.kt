package com.johnturkson.awstools.dynamodb.requestbuilder.requests

import com.johnturkson.awstools.dynamodb.objectbuilder.DynamoDBObject
import com.johnturkson.awstools.dynamodb.requestbuilder.data.ProjectionExpression
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueryRequest<T>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("IndexName")
    val indexName: String? = null,
    @SerialName("Limit")
    val limit: Int? = null,
    @SerialName("KeyConditionExpression")
    val keyConditionExpression: String,
    @SerialName("ExclusiveStartKey")
    val exclusiveStartKey: DynamoDBObject? = null,
    @SerialName("ExpressionAttributeNames")
    val expressionAttributeNames: Map<String, String>? = null,
    @SerialName("ExpressionAttributeValues")
    val expressionAttributeValues: DynamoDBObject? = null,
    @SerialName("ProjectionExpression")
    val projectionExpression: ProjectionExpression? = null,
    @SerialName("FilterExpression")
    val filterExpression: String? = null,
    @SerialName("ConsistentRead")
    val consistentRead: Boolean? = null,
    @SerialName("ScanIndexForward")
    val scanIndexForward: Boolean? = null,
)

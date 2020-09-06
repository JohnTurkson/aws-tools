package com.johnturkson.awstools.dynamodb.request

import com.johnturkson.awstools.dynamodb.objectbuilder.DynamoDBObject
import com.johnturkson.awstools.dynamodb.request.components.ProjectionExpression
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScanRequest<T>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Limit")
    val limit: Int? = null,
    @SerialName("ExclusiveStartKey")
    val exclusiveStartKey: DynamoDBObject? = null,
    @SerialName("ScanFilter")
    val scanFilter: DynamoDBObject? = null,
    @SerialName("ProjectionExpression")
    val projectionExpression: ProjectionExpression? = null,
    @SerialName("FilterExpression")
    val filterExpression: String? = null,
    @SerialName("ConsistentRead")
    val consistentRead: Boolean? = null,
)

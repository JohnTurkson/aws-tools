package com.johnturkson.aws.dynamodb.requests

import com.johnturkson.aws.dynamodb.data.AttributeValueMap
import com.johnturkson.aws.dynamodb.data.ProjectionExpression
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScanRequest<K>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Limit")
    val limit: Int? = null,
    @SerialName("ExclusiveStartKey")
    val exclusiveStartKey: K? = null,
    @SerialName("ExpressionAttributeNames")
    val expressionAttributeNames: Map<String, String>? = null,
    @SerialName("ExpressionAttributeValues")
    val expressionAttributeValues: AttributeValueMap? = null,
    @SerialName("ProjectionExpression")
    val projectionExpression: ProjectionExpression? = null,
    @SerialName("FilterExpression")
    val filterExpression: String? = null,
    @SerialName("ConsistentRead")
    val consistentRead: Boolean? = null,
)

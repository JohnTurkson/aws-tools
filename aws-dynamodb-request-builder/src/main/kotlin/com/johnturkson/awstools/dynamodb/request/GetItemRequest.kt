package com.johnturkson.awstools.dynamodb.request

import com.johnturkson.awstools.dynamodb.objectbuilder.DynamoDBObject
import com.johnturkson.awstools.dynamodb.request.components.ProjectionExpression
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetItemRequest<T>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Key")
    val key: DynamoDBObject,
    @SerialName("ProjectionExpression")
    val projectionExpression: ProjectionExpression? = null,
    @SerialName("ConsistentRead")
    val consistentRead: Boolean? = null,
)

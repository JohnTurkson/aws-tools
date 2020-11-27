package com.johnturkson.awstools.dynamodb.requestbuilder.requests

import com.johnturkson.awstools.dynamodb.objectbuilder.DynamoDBObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateItemRequest<T>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Key")
    val key: DynamoDBObject,
    @SerialName("ReturnValues")
    val returnValues: String? = null,
    @SerialName("UpdateExpression")
    val updateExpression: String? = null,
    @SerialName("ConditionExpression")
    val conditionExpression: String? = null,
    @SerialName("ExpressionAttributeNames")
    val expressionAttributeNames: Map<String, String>? = null,
    @SerialName("ExpressionAttributeValues")
    val expressionAttributeValues: DynamoDBObject? = null,
)

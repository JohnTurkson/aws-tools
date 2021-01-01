package com.johnturkson.awstools.dynamodb.requests

import com.johnturkson.awstools.dynamodb.data.AttributeValueMap
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PutItemRequest<T>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Item")
    val item: T,
    @SerialName("ReturnValues")
    val returnValues: String? = null,
    @SerialName("ConditionExpression")
    val conditionExpression: String? = null,
    @SerialName("ExpressionAttributeNames")
    val expressionAttributeNames: Map<String, String>? = null,
    @SerialName("ExpressionAttributeValues")
    val expressionAttributeValues: AttributeValueMap? = null,
)

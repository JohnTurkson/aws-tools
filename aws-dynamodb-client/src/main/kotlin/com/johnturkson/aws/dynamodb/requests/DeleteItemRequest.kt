package com.johnturkson.aws.dynamodb.requests

import com.johnturkson.aws.dynamodb.data.AttributeValueMap
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteItemRequest<T>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Key")
    val key: T,
    @SerialName("ReturnValues")
    val returnValues: String? = null,
    @SerialName("ConditionExpression")
    val conditionExpression: String? = null,
    @SerialName("ExpressionAttributeNames")
    val expressionAttributeNames: Map<String, String>? = null,
    @SerialName("ExpressionAttributeValues")
    val expressionAttributeValues: AttributeValueMap? = null,
)

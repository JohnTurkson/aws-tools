package com.johnturkson.awstools.dynamodb.request

import com.johnturkson.awstools.dynamodb.objectbuilder.DynamoDBObject
import com.johnturkson.awstools.dynamodb.request.serializers.PutItemRequestSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = PutItemRequestSerializer::class)
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
    val expressionAttributeValues: DynamoDBObject? = null,
)

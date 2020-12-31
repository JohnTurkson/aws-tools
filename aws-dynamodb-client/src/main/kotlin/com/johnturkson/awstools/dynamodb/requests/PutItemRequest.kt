package com.johnturkson.awstools.dynamodb.requests

import com.johnturkson.awstools.dynamodb.data.AttributeValueMap
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param T The typed `Item` attribute representing the object to be inserted into the table.
 * This parameter must consist of a class containing only `AttributeValue` fields.
 * If no explicit class representing a specific `Item` object exists, [AttributeValueMap] objects constructed via the `Item` DSL may be used to dynamically create `Item` objects on-demand.
 */
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

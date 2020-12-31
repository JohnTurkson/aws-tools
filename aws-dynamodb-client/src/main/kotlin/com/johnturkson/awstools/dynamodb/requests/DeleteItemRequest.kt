package com.johnturkson.awstools.dynamodb.requests

import com.johnturkson.awstools.dynamodb.data.AttributeValueMap
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param K The typed `Key` attribute representing the primary key of the item to be deleted.
 * This parameter must consist of a class containing only `AttributeValue` fields.
 * If no explicit class representing a specific `Key` object exists, [AttributeValueMap] objects constructed via the `Key` DSL may be used to dynamically create `Key` objects on-demand.
 */
@Serializable
data class DeleteItemRequest<K>(
    @SerialName("TableName")
    val tableName: String,
    @SerialName("Key")
    val key: K,
    @SerialName("ReturnValues")
    val returnValues: String? = null,
    @SerialName("ConditionExpression")
    val conditionExpression: String? = null,
    @SerialName("ExpressionAttributeNames")
    val expressionAttributeNames: Map<String, String>? = null,
    @SerialName("ExpressionAttributeValues")
    val expressionAttributeValues: AttributeValueMap? = null,
)

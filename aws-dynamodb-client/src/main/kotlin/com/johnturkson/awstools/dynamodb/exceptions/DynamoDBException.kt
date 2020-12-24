package com.johnturkson.awstools.dynamodb.exceptions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class DynamoDBException : Exception() {
    @SerialName("Message")
    abstract override val message: String
    
}

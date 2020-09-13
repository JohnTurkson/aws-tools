package com.johnturkson.awstools.dynamodb.request

import com.johnturkson.awstools.dynamodb.objectbuilder.DynamoDBObject
import com.johnturkson.awstools.dynamodb.request.serializers.ScanResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = ScanResponseSerializer::class)
data class ScanResponse<T>(
    @SerialName("Count")
    val count: Int,
    @SerialName("Items")
    val items: List<T>,
    @SerialName("ScannedCount")
    val scannedCount: Int,
    @SerialName("LastEvaluatedKey")
    val lastEvaluatedKey: DynamoDBObject? = null,
)

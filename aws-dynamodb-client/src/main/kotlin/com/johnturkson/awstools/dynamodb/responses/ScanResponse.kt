package com.johnturkson.awstools.dynamodb.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScanResponse<K, T>(
    @SerialName("Count")
    val count: Int,
    @SerialName("Items")
    val items: List<T>,
    @SerialName("ScannedCount")
    val scannedCount: Int,
    @SerialName("LastEvaluatedKey")
    val lastEvaluatedKey: K? = null,
)

package com.johnturkson.aws.qldb.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommitTransactionRequestData(
    @SerialName("CommitDigest") val commitDigest: String,
    @SerialName("TransactionId") val transactionId: String,
)

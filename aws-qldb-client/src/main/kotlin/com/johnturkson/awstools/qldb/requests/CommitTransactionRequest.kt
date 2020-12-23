package com.johnturkson.awstools.qldb.requests

import com.johnturkson.awstools.qldb.data.CommitTransactionRequestData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommitTransactionRequest(
    @SerialName("SessionToken") val sessionToken: String,
    @SerialName("CommitTransaction") val commitTransaction: CommitTransactionRequestData,
)

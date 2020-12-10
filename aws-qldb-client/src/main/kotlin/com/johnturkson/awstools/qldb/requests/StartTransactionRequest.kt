package com.johnturkson.awstools.qldb.requests

import com.johnturkson.awstools.qldb.data.StartTransactionRequestData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartTransactionRequest(
    @SerialName("SessionToken") val sessionToken: String,
    @SerialName("StartTransaction") val startTransaction: StartTransactionRequestData,
)

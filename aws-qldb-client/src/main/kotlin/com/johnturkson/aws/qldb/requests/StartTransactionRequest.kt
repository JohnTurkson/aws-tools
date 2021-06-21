package com.johnturkson.aws.qldb.requests

import com.johnturkson.aws.qldb.data.StartTransactionRequestData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartTransactionRequest(
    @SerialName("SessionToken") val sessionToken: String,
    @SerialName("StartTransaction") val startTransaction: StartTransactionRequestData,
)

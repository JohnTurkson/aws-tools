package com.johnturkson.awstools.qldb.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExecuteStatementRequestData(
    @SerialName("Statement") val statement: String,
    @SerialName("TransactionId") val transactionId: String,
)

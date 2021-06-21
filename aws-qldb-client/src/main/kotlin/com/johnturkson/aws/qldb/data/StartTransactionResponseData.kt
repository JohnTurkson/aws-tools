package com.johnturkson.aws.qldb.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartTransactionResponseData(@SerialName("TransactionId") val transactionId: String)

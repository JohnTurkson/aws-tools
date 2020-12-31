package com.johnturkson.awstools.qldb.responses

import com.johnturkson.awstools.qldb.data.StartTransactionResponseData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartTransactionResponse(@SerialName("StartTransaction") val startTransaction: StartTransactionResponseData)

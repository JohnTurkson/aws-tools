package com.johnturkson.aws.qldb.responses

import com.johnturkson.aws.qldb.data.StartTransactionResponseData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartTransactionResponse(@SerialName("StartTransaction") val startTransaction: StartTransactionResponseData)

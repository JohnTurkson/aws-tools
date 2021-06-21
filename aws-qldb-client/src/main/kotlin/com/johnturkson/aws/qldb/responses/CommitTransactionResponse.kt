package com.johnturkson.aws.qldb.responses

import com.johnturkson.aws.qldb.data.CommitTransactionResponseData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommitTransactionResponse(@SerialName("CommitTransaction") val commitTransaction: CommitTransactionResponseData)

package com.johnturkson.awstools.qldb.responses

import com.johnturkson.awstools.qldb.data.CommitTransactionResponseData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommitTransactionResponse(@SerialName("CommitTransaction") val commitTransaction: CommitTransactionResponseData)

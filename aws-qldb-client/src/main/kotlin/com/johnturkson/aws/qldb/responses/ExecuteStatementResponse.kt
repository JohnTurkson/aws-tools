package com.johnturkson.aws.qldb.responses

import com.johnturkson.aws.qldb.data.ExecuteStatementResponseData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExecuteStatementResponse(@SerialName("ExecuteStatement") val executeStatement: ExecuteStatementResponseData)

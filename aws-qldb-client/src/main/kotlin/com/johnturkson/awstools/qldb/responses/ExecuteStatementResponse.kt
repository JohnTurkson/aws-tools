package com.johnturkson.awstools.qldb.responses

import com.johnturkson.awstools.qldb.data.ExecuteStatementResponseData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExecuteStatementResponse(@SerialName("ExecuteStatement") val executeStatement: ExecuteStatementResponseData)

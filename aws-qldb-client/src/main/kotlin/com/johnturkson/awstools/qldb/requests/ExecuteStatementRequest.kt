package com.johnturkson.awstools.qldb.requests

import com.johnturkson.awstools.qldb.data.ExecuteStatementRequestData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExecuteStatementRequest(
    @SerialName("SessionToken") val sessionToken: String,
    @SerialName("ExecuteStatement") val executeStatementData: ExecuteStatementRequestData,
)

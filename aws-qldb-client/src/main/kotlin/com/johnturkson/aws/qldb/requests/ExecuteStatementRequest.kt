package com.johnturkson.aws.qldb.requests

import com.johnturkson.aws.qldb.data.ExecuteStatementRequestData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExecuteStatementRequest(
    @SerialName("SessionToken") val sessionToken: String,
    @SerialName("ExecuteStatement") val executeStatementData: ExecuteStatementRequestData,
)

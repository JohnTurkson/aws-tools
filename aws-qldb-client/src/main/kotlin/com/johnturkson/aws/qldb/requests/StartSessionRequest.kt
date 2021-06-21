package com.johnturkson.aws.qldb.requests

import com.johnturkson.aws.qldb.data.StartSessionRequestData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartSessionRequest(@SerialName("StartSession") val startSession: StartSessionRequestData)

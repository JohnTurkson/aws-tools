package com.johnturkson.awstools.qldb.requests

import com.johnturkson.awstools.qldb.data.StartSessionRequestData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartSessionRequest(@SerialName("StartSession") val startSession: StartSessionRequestData)

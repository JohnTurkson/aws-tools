package com.johnturkson.awstools.qldb.actions

import com.johnturkson.awstools.qldb.requests.StartSessionRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartSession(@SerialName("StartSession") val startSession: StartSessionRequest)

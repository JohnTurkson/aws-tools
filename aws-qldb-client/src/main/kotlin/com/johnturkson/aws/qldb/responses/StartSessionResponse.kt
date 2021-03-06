package com.johnturkson.aws.qldb.responses

import com.johnturkson.aws.qldb.data.StartSessionResponseData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartSessionResponse(@SerialName("StartSession") val startSession: StartSessionResponseData)

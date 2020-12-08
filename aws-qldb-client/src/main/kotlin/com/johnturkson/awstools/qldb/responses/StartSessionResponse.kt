package com.johnturkson.awstools.qldb.responses

import com.johnturkson.awstools.qldb.data.SessionToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartSessionResponse(@SerialName("StartSession") val startSession: SessionToken)

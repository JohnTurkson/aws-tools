package com.johnturkson.awstools.qldb.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionToken(@SerialName("SessionToken") val sessionToken: String)

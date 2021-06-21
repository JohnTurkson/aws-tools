package com.johnturkson.aws.ses.requestbuilder.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageTag(@SerialName("Name") val name: String, @SerialName("Value") val value: String)

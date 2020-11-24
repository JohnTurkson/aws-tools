package com.johnturkson.awstools.ses.requestbuilder.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(@SerialName("Body") val body: Body, @SerialName("Subject") val subject: Content)

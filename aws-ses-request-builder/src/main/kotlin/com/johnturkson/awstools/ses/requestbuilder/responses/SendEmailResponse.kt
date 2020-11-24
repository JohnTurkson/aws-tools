package com.johnturkson.awstools.ses.requestbuilder.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendEmailResponse(@SerialName("MessageId") val messageId: String)

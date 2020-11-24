package com.johnturkson.awstools.ses.requestbuilder.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Destination(
    @SerialName("ToAddresses")
    val to: List<String>,
    @SerialName("CcAddresses")
    val cc: List<String>,
    @SerialName("BccAddresses")
    val bcc: List<String>,
)

package com.johnturkson.awstools.ses.requestbuilder.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Content(
    @SerialName("Data") val data: String,
    @SerialName("Charset") val charset: String? = null,
)

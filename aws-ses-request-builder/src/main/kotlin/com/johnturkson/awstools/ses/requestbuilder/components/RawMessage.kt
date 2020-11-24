package com.johnturkson.awstools.ses.requestbuilder.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawMessage(@SerialName("Data") val data: String)

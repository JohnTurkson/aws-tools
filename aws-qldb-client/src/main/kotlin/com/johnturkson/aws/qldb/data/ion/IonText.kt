package com.johnturkson.aws.qldb.data.ion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IonText(@SerialName("IonText") val ionText: String)

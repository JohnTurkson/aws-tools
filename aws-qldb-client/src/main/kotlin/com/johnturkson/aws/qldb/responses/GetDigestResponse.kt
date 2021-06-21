package com.johnturkson.aws.qldb.responses

import com.johnturkson.aws.qldb.data.ion.IonText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDigestResponse(@SerialName("Digest") val digest: String, @SerialName("DigestTipAddress") val digestTipAddress: IonText)

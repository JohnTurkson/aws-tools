package com.johnturkson.awstools.qldb.responses

import com.johnturkson.awstools.qldb.data.ion.IonText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDigestResponse(@SerialName("Digest") val digest: String, @SerialName("DigestTipAddress") val digestTipAddress: IonText)

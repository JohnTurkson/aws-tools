package com.johnturkson.awstools.qldb.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartSessionRequestData(@SerialName("LedgerName") val ledgerName: String)

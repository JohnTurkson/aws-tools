package com.johnturkson.awstools.qldb.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StartSessionRequest(@SerialName("LedgerName") val ledgerName: String)

package com.johnturkson.aws.qldb.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExecuteStatementResponseData(
    @SerialName("ConsumedIOs") val consumedIOs: ConsumedIOs,
    @SerialName("TimingInformation") val timingInformation: TimingInformation,
) {
    @Serializable
    data class ConsumedIOs(@SerialName("ReadIOs") val readIOs: Int, @SerialName("WriteIOs") val writeIOs: Int)
    
    @Serializable
    data class TimingInformation(@SerialName("ProcessingTimeMilliseconds") val processingTimeMilliseconds: Int)
}

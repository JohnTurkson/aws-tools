package com.johnturkson.aws.ses.requestbuilder.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListManagementOptions(
    @SerialName("ContactListName")
    val contactListName: String,
    @SerialName("TopicName")
    val topicName: String? = null,
)

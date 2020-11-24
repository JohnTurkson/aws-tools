package com.johnturkson.awstools.ses.requestbuilder.requests

import com.johnturkson.awstools.ses.requestbuilder.components.Destination
import com.johnturkson.awstools.ses.requestbuilder.components.EmailContent
import com.johnturkson.awstools.ses.requestbuilder.components.ListManagementOptions
import com.johnturkson.awstools.ses.requestbuilder.components.MessageTag
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendEmailRequest(
    @SerialName("FromEmailAddress")
    val from: String,
    @SerialName("Destination")
    val destination: Destination,
    @SerialName("Content")
    val content: EmailContent,
    @SerialName("ReplyToAddresses")
    val replyToAddresses: List<String>? = null,
    @SerialName("FromEmailAddressIdentityArn")
    val fromEmailAddressIdentityArn: String? = null,
    @SerialName("FeedbackForwardingEmailAddress")
    val feedbackForwardingEmailAddress: String? = null,
    @SerialName("FeedbackForwardingEmailAddressIdentityArn")
    val feedbackForwardingEmailAddressIdentityArn: String? = null,
    @SerialName("EmailTags")
    val emailTags: List<MessageTag>? = null,
    @SerialName("ListManagementOptions")
    val listManagementOptions: ListManagementOptions? = null,
    @SerialName("ConfigurationSetName")
    val configurationSetName: String? = null,
)

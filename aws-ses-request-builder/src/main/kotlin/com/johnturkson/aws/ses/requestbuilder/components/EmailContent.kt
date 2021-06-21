package com.johnturkson.aws.ses.requestbuilder.components

import com.johnturkson.aws.ses.requestbuilder.serializers.EmailContentSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = EmailContentSerializer::class)
sealed class EmailContent {
    @Serializable
    data class RawEmailContent(@SerialName("Raw") val raw: RawMessage) : EmailContent()
    
    @Serializable
    data class SimpleEmailContent(@SerialName("Simple") val message: Message) : EmailContent()
    
    @Serializable
    data class TemplateEmailContent(@SerialName("Template") val template: Template) : EmailContent()
}

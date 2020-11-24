package com.johnturkson.awstools.ses.requestbuilder.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Template(
    @SerialName("TemplateArn")
    val arn: String,
    @SerialName("TemplateData")
    val data: String,
    @SerialName("TemplateName")
    val name: String,
)

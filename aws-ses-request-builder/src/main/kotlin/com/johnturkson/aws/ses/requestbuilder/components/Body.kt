package com.johnturkson.aws.ses.requestbuilder.components

import com.johnturkson.aws.ses.requestbuilder.serializers.BodySerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = BodySerializer::class)
sealed class Body {
    @Serializable
    data class TextBody(@SerialName("Text") val text: Content) : Body()
    
    @Serializable
    data class HtmlBody(@SerialName("Html") val html: Content) : Body()
}

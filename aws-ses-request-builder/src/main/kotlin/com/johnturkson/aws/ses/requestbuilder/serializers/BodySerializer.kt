package com.johnturkson.aws.ses.requestbuilder.serializers

import com.johnturkson.aws.ses.requestbuilder.components.Body
import com.johnturkson.aws.ses.requestbuilder.components.Body.HtmlBody
import com.johnturkson.aws.ses.requestbuilder.components.Body.TextBody
import com.johnturkson.aws.ses.requestbuilder.components.Content
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure

class BodySerializer : KSerializer<Body> {
    @InternalSerializationApi
    override val descriptor: SerialDescriptor = buildSerialDescriptor("Body", SerialKind.CONTEXTUAL) {
        element("Html", HtmlBody.serializer().descriptor)
        element("Text", TextBody.serializer().descriptor)
    }
    
    override fun serialize(encoder: Encoder, value: Body) {
        when (value) {
            is HtmlBody -> encoder.encodeSerializableValue(HtmlBody.serializer(), value)
            is TextBody -> encoder.encodeSerializableValue(TextBody.serializer(), value)
        }
    }
    
    @InternalSerializationApi
    override fun deserialize(decoder: Decoder): Body {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> HtmlBody(decodeSerializableElement(
                    Content.serializer().descriptor,
                    index,
                    Content.serializer()
                ))
                1 -> TextBody(decodeSerializableElement(
                    Content.serializer().descriptor,
                    index,
                    Content.serializer()
                ))
                else -> throw Exception()
            }
        }
    }
}

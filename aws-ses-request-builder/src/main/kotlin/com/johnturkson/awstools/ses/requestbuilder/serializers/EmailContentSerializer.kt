package com.johnturkson.awstools.ses.requestbuilder.serializers

import com.johnturkson.awstools.ses.requestbuilder.components.EmailContent
import com.johnturkson.awstools.ses.requestbuilder.components.EmailContent.*
import com.johnturkson.awstools.ses.requestbuilder.components.Message
import com.johnturkson.awstools.ses.requestbuilder.components.RawMessage
import com.johnturkson.awstools.ses.requestbuilder.components.Template
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure

class EmailContentSerializer : KSerializer<EmailContent> {
    @InternalSerializationApi
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("EmailContent") {
        element("Raw", RawEmailContent.serializer().descriptor)
        element("Simple", SimpleEmailContent.serializer().descriptor)
        element("Template", TemplateEmailContent.serializer().descriptor)
    }
    
    override fun serialize(encoder: Encoder, value: EmailContent) {
        when (value) {
            is RawEmailContent -> encoder.encodeSerializableValue(RawEmailContent.serializer(), value)
            is SimpleEmailContent -> encoder.encodeSerializableValue(SimpleEmailContent.serializer(), value)
            is TemplateEmailContent -> encoder.encodeSerializableValue(TemplateEmailContent.serializer(), value)
        }
    }
    
    @InternalSerializationApi
    override fun deserialize(decoder: Decoder): EmailContent {
        return decoder.decodeStructure(descriptor) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> RawEmailContent(decodeSerializableElement(
                    RawMessage.serializer().descriptor,
                    index,
                    RawMessage.serializer(),
                ))
                1 -> SimpleEmailContent(decodeSerializableElement(
                    Message.serializer().descriptor,
                    index,
                    Message.serializer(),
                ))
                2 -> TemplateEmailContent(decodeSerializableElement(
                    Template.serializer().descriptor,
                    index,
                    Template.serializer(),
                ))
                else -> throw Exception()
            }
        }
    }
}

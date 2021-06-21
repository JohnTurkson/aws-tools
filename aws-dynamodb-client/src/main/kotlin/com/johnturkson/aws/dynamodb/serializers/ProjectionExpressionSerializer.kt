package com.johnturkson.aws.dynamodb.serializers

import com.johnturkson.aws.dynamodb.data.ProjectionExpression
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class ProjectionExpressionSerializer : KSerializer<ProjectionExpression> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ProjectionExpression", PrimitiveKind.STRING)
    
    override fun serialize(encoder: Encoder, value: ProjectionExpression) {
        encoder.encodeString(value.attributes.joinToString(separator = ", "))
    }
    
    override fun deserialize(decoder: Decoder): ProjectionExpression {
        return ProjectionExpression(decoder.decodeString().split(", "))
    }
}

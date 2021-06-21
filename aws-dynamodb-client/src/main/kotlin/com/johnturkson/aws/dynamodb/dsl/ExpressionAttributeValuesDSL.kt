package com.johnturkson.aws.dynamodb.dsl

import com.johnturkson.aws.client.configuration.SharedJsonSerializer
import com.johnturkson.aws.dynamodb.builders.AttributeValueMapBuilder
import com.johnturkson.aws.dynamodb.data.AttributeValueMap
import kotlinx.serialization.json.Json

fun ExpressionAttributeValues(
    serializer: Json = SharedJsonSerializer.instance,
    builder: AttributeValueMapBuilder.() -> Unit,
): AttributeValueMap {
    return AttributeValueMapBuilder(serializer).apply { builder() }.build()
}

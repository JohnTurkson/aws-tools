package com.johnturkson.awstools.dynamodb.dsl

import com.johnturkson.awstools.client.configuration.SharedJsonSerializer
import com.johnturkson.awstools.dynamodb.builders.AttributeValueMapBuilder
import com.johnturkson.awstools.dynamodb.data.AttributeValueMap
import kotlinx.serialization.json.Json

fun Item(
    serializer: Json = SharedJsonSerializer.instance,
    builder: AttributeValueMapBuilder.() -> Unit,
): AttributeValueMap {
    return AttributeValueMapBuilder(serializer).apply { builder() }.build()
}

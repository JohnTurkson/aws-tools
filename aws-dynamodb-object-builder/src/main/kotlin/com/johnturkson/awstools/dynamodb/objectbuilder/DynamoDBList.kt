package com.johnturkson.awstools.dynamodb.objectbuilder

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray

@Serializable(with = DynamoDBListSerializer::class)
data class DynamoDBList internal constructor(internal val value: JsonArray)

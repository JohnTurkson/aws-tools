package com.johnturkson.awstools.dynamodb.objectbuilder

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable(with = DynamoDBObjectSerializer::class)
data class DynamoDBObject internal constructor(internal val value: JsonObject)

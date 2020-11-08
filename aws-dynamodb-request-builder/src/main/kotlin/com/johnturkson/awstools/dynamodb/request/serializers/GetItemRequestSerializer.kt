package com.johnturkson.awstools.dynamodb.request.serializers

import com.johnturkson.awstools.dynamodb.request.GetItemRequest
import com.johnturkson.awstools.dynamodb.request.components.ProjectionExpression
import com.johnturkson.awstools.dynamodb.transformingserializer.DynamoDBTransformingSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

class GetItemRequestSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<GetItemRequest<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    
    override fun serialize(encoder: Encoder, value: GetItemRequest<T>) {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json = (encoder as JsonEncoder).json
        val tableName = JsonPrimitive(value.tableName)
        val key = json.encodeToJsonElement(transformer, value.key)
        val expressionAttributeNames = json.encodeToJsonElement(
            MapSerializer(String.serializer(), String.serializer()).nullable,
            value.expressionAttributeNames
        )
        val projectionExpression = json.encodeToJsonElement(
            ProjectionExpression.serializer().nullable,
            value.projectionExpression
        )
        val consistentRead = JsonPrimitive(value.consistentRead)
        val request = JsonObject(mapOf(
            "TableName" to tableName,
            "Key" to key,
            "ExpressionAttributeNames" to expressionAttributeNames,
            "ProjectionExpression" to projectionExpression,
            "ConsistentRead" to consistentRead,
        ))
        encoder.encodeJsonElement(request)
    }
    
    override fun deserialize(decoder: Decoder): GetItemRequest<T> {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json = (decoder as JsonDecoder).json
        val request = decoder.decodeJsonElement() as JsonObject
        val tableName = json.decodeFromJsonElement(String.serializer(), request["TableName"]!!)
        val key = json.decodeFromJsonElement(transformer, request["Key"]!!)
        val expressionAttributeNames = json.decodeFromJsonElement(
            MapSerializer(String.serializer(), String.serializer()).nullable,
            request["ExpressionAttributeNames"] ?: JsonNull
        )
        val projectionExpression = json.decodeFromJsonElement(
            ProjectionExpression.serializer().nullable,
            request["ProjectionExpression"] ?: JsonNull
        )
        val consistentRead = json.decodeFromJsonElement(
            Boolean.serializer().nullable,
            request["ConsistentRead"] ?: JsonNull
        )
        return GetItemRequest(tableName, key, expressionAttributeNames, projectionExpression, consistentRead)
    }
}

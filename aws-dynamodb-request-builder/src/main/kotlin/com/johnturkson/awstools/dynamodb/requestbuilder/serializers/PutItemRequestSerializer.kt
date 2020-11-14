package com.johnturkson.awstools.dynamodb.requestbuilder.serializers

import com.johnturkson.awstools.dynamodb.objectbuilder.DynamoDBObject
import com.johnturkson.awstools.dynamodb.transformingserializer.DynamoDBTransformingSerializer
import com.johnturkson.awstools.dynamodb.requestbuilder.requests.PutItemRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

class PutItemRequestSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<PutItemRequest<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor
    
    override fun serialize(encoder: Encoder, value: PutItemRequest<T>) {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json =  (encoder as JsonEncoder).json
        val tableName = JsonPrimitive(value.tableName)
        val item = json.encodeToJsonElement(transformer, value.item)
        val returnValues = JsonPrimitive(value.returnValues)
        val conditionExpression = JsonPrimitive(value.conditionExpression)
        val expressionAttributeNames = json.encodeToJsonElement(
            MapSerializer(String.serializer(), String.serializer()).nullable,
            value.expressionAttributeNames,
        )
        val expressionAttributeValues = json.encodeToJsonElement(
            DynamoDBObject.serializer().nullable,
            value.expressionAttributeValues,
        )
        val request = JsonObject(mapOf(
            "TableName" to tableName,
            "Item" to item,
            "ReturnValues" to returnValues,
            "ConditionExpression" to conditionExpression,
            "ExpressionAttributeNames" to expressionAttributeNames,
            "ExpressionAttributeValues" to expressionAttributeValues,
        ))
        encoder.encodeJsonElement(request)
    }
    
    override fun deserialize(decoder: Decoder): PutItemRequest<T> {
        val transformer = DynamoDBTransformingSerializer(dataSerializer)
        val json = (decoder as JsonDecoder).json
        val request = decoder.decodeJsonElement() as JsonObject
        val tableName = request["TableName"]!!.jsonPrimitive.content
        val item = json.decodeFromJsonElement(transformer, request["Item"]!!)
        val returnValues = request["ReturnValues"]?.jsonPrimitive?.content
        val conditionExpression = request["ConditionExpression"]?.jsonPrimitive?.content
        val expressionAttributeNames = json.decodeFromJsonElement(
            MapSerializer(String.serializer(), String.serializer()).nullable,
            request["ExpressionAttributeNames"] ?: JsonNull
        )
        val expressionAttributeValues = json.decodeFromJsonElement(
            DynamoDBObject.serializer().nullable,
            request["ExpressionAttributeValues"] ?: JsonNull
        )
        return PutItemRequest(
            tableName,
            item,
            returnValues,
            conditionExpression,
            expressionAttributeNames,
            expressionAttributeValues,
        )
    }
}

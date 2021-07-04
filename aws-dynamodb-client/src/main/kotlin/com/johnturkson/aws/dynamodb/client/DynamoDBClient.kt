package com.johnturkson.aws.dynamodb.client

import com.johnturkson.aws.client.AWSClient
import com.johnturkson.aws.client.configuration.AWSCredentials
import com.johnturkson.aws.dynamodb.configuration.DynamoDBConfiguration
import com.johnturkson.aws.dynamodb.exceptions.DynamoDBException
import com.johnturkson.aws.dynamodb.requests.*
import com.johnturkson.aws.dynamodb.responses.*
import com.johnturkson.aws.dynamodb.serializers.*
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class DynamoDBClient(
    credentials: AWSCredentials? = null,
    region: String? = null,
    client: HttpClient? = null,
    serializer: Json? = null,
) : AWSClient(credentials, region, client, serializer) {
    suspend inline fun <reified T> putItem(
        request: PutItemRequest<T>,
        dataSerializer: KSerializer<T> = serializer(),
    ): PutItemResponse<T> {
        val target = "DynamoDB_20120810.PutItem"
        return request(
            target,
            request,
            PutItemRequestSerializer(dataSerializer),
            PutItemResponseSerializer(dataSerializer),
        )
    }
    
    suspend inline fun <reified K, reified T> getItem(
        request: GetItemRequest<K>,
        keySerializer: KSerializer<K> = serializer(),
        dataSerializer: KSerializer<T> = serializer(),
    ): GetItemResponse<T> {
        val target = "DynamoDB_20120810.GetItem"
        return request(
            target,
            request,
            GetItemRequestSerializer(keySerializer),
            GetItemResponseSerializer(dataSerializer),
        )
    }
    
    suspend inline fun <reified K, reified T> updateItem(
        request: UpdateItemRequest<K>,
        keySerializer: KSerializer<K>,
        dataSerializer: KSerializer<T>,
    ): UpdateItemResponse<T> {
        val target = "DynamoDB_20120810.UpdateItem"
        return request(
            target,
            request,
            UpdateItemRequestSerializer(keySerializer),
            UpdateItemResponseSerializer(dataSerializer),
        )
    }
    
    suspend inline fun <reified K, reified T> deleteItem(
        request: DeleteItemRequest<K>,
        keySerializer: KSerializer<K> = serializer(),
        dataSerializer: KSerializer<T> = serializer(),
    ): DeleteItemResponse<T> {
        val target = "DynamoDB_20120810.DeleteItem"
        return request(
            target,
            request,
            DeleteItemRequestSerializer(keySerializer),
            DeleteItemResponseSerializer(dataSerializer),
        )
    }
    
    suspend inline fun <reified K, reified T> query(
        request: QueryRequest<K>,
        keySerializer: KSerializer<K>,
        dataSerializer: KSerializer<T>,
    ): QueryResponse<K, T> {
        val target = "DynamoDB_20120810.Query"
        return request(
            target,
            request,
            QueryRequestSerializer(keySerializer),
            QueryResponseSerializer(keySerializer, dataSerializer),
        )
    }
    
    suspend inline fun <reified K, reified T> scan(
        request: ScanRequest<K>,
        keySerializer: KSerializer<K>,
        dataSerializer: KSerializer<T>,
    ): ScanResponse<K, T> {
        val target = "DynamoDB_20120810.Scan"
        return request(
            target,
            request,
            ScanRequestSerializer(keySerializer),
            ScanResponseSerializer(keySerializer, dataSerializer),
        )
    }
    
    @PublishedApi
    internal suspend inline fun <reified T, reified R> request(
        target: String,
        request: T,
        requestSerializer: KSerializer<T>,
        responseSerializer: KSerializer<R>,
    ): R {
        val configuration = DynamoDBConfiguration(region)
        val body = serializer.encodeToString(requestSerializer, request)
        val headers = generateHeaders(target, body)
        val (statusCode, responseBody) = makeRequest(configuration, body, headers)
        return when (statusCode) {
            200 -> serializer.decodeFromString(responseSerializer, responseBody)
            else -> throw serializer.decodeFromString(serializer<DynamoDBException>(), responseBody)
        }
    }
}

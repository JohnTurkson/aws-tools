package com.johnturkson.awstools.dynamodb.client

import com.johnturkson.awstools.client.AWSClient
import com.johnturkson.awstools.client.configuration.AWSCredentials
import com.johnturkson.awstools.dynamodb.configuration.DynamoDBConfiguration
import com.johnturkson.awstools.dynamodb.exceptions.DynamoDBException
import com.johnturkson.awstools.dynamodb.requests.*
import com.johnturkson.awstools.dynamodb.responses.*
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class DynamoDBClient(
    credentials: AWSCredentials? = null,
    region: String? = null,
    client: HttpClient? = null,
    serializer: Json? = null,
) : AWSClient(credentials, region, client, serializer) {
    
    suspend inline fun <reified T> putItem(request: PutItemRequest<T>): PutItemResponse<T> {
        val target = "DynamoDB_20120810.PutItem"
        return request(target, request)
    }
    
    suspend inline fun <reified K, reified T> getItem(request: GetItemRequest<K>): GetItemResponse<T> {
        val target = "DynamoDB_20120810.GetItem"
        return request(target, request)
    }
    
    suspend inline fun <reified K, reified T> updateItem(request: UpdateItemRequest<K>): UpdateItemResponse<T> {
        val target = "DynamoDB_20120810.UpdateItem"
        return request(target, request)
    }
    
    suspend inline fun <reified K, reified T> deleteItem(request: DeleteItemRequest<K>): DeleteItemResponse<T> {
        val target = "DynamoDB_20120810.DeleteItem"
        return request(target, request)
    }
    
    suspend inline fun <reified K, reified T> query(request: QueryRequest<K>): QueryResponse<K, T> {
        val target = "DynamoDB_20120810.Query"
        return request(target, request)
    }
    
    suspend inline fun <reified K, reified T> scan(request: ScanRequest<K>): ScanResponse<K, T> {
        val target = "DynamoDB_20120810.Scan"
        return request(target, request)
    }
    
    @PublishedApi
    internal suspend inline fun <reified T, reified R> request(target: String, request: T): R {
        val configuration = DynamoDBConfiguration(region)
        val body = serializer.encodeToString(serializer(), request)
        val headers = generateHeaders(target, body)
        val (statusCode, responseBody) = makeRequest(configuration, body, headers)
        return when (statusCode) {
            200 -> serializer.decodeFromString(serializer(), responseBody)
            else -> throw serializer.decodeFromString(serializer<DynamoDBException>(), responseBody)
        }
    }
}

package com.johnturkson.awstools.dynamodb.requesthandler

import com.johnturkson.awstools.dynamodb.requestbuilder.requests.*
import com.johnturkson.awstools.dynamodb.requestbuilder.responses.*
import com.johnturkson.awstools.requesthandler.AWSCredentials
import com.johnturkson.awstools.requesthandler.AWSRequestHandler
import com.johnturkson.awstools.requestsigner.AWSRequestSigner.Header
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class DynamoDBRequestHandler(
    override val credentials: AWSCredentials,
    override val client: HttpClient,
    private val region: String,
    private val serializer: Json,
) : AWSRequestHandler {
    suspend fun <T> getItem(
        request: GetItemRequest,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): GetItemResponse<T> {
        val target = "DynamoDB_20120810.GetItem"
        val configuration = DynamoDBConfiguration(region)
        val requestSerializer = GetItemRequest.serializer()
        val responseSerializer = GetItemResponse.serializer(typeSerializer)
        return request(configuration, request, headers, target, requestSerializer, responseSerializer)
    }
    
    suspend fun <T> putItem(
        request: PutItemRequest<T>,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): PutItemResponse<T> {
        val target = "DynamoDB_20120810.PutItem"
        val configuration = DynamoDBConfiguration(region)
        val requestSerializer = PutItemRequest.serializer(typeSerializer)
        val responseSerializer = PutItemResponse.serializer(typeSerializer)
        return request(configuration, request, headers, target, requestSerializer, responseSerializer)
    }
    
    suspend fun <T> updateItem(
        request: UpdateItemRequest,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): UpdateItemResponse<T> {
        val target = "DynamoDB_20120810.UpdateItem"
        val configuration = DynamoDBConfiguration(region)
        val requestSerializer = UpdateItemRequest.serializer()
        val responseSerializer = UpdateItemResponse.serializer(typeSerializer)
        return request(configuration, request, headers, target, requestSerializer, responseSerializer)
    }
    
    suspend fun <T> deleteItem(
        request: DeleteItemRequest,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): DeleteItemResponse<T> {
        val target = "DynamoDB_20120810.DeleteItem"
        val configuration = DynamoDBConfiguration(region)
        val requestSerializer = DeleteItemRequest.serializer()
        val responseSerializer = DeleteItemResponse.serializer(typeSerializer)
        return request(configuration, request, headers, target, requestSerializer, responseSerializer)
    }
    
    suspend fun <T> query(
        request: QueryRequest,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): QueryResponse<T> {
        val target = "DynamoDB_20120810.Query"
        val configuration = DynamoDBConfiguration(region)
        val requestSerializer = QueryRequest.serializer()
        val responseSerializer = QueryResponse.serializer(typeSerializer)
        return request(configuration, request, headers, target, requestSerializer, responseSerializer)
    }
    
    suspend fun <T> scan(
        request: ScanRequest,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): ScanResponse<T> {
        val target = "DynamoDB_20120810.Scan"
        val configuration = DynamoDBConfiguration(region)
        val requestSerializer = ScanRequest.serializer()
        val responseSerializer = ScanResponse.serializer(typeSerializer)
        return request(configuration, request, headers, target, requestSerializer, responseSerializer)
    }
    
    private suspend fun <T, R> request(
        configuration: DynamoDBConfiguration,
        request: T,
        headers: List<Header>,
        target: String,
        requestSerializer: KSerializer<T>,
        responseSerializer: KSerializer<R>,
    ): R {
        val body = serializer.encodeToString(requestSerializer, request)
        val targetHeaders = generateTargetHeaders(target)
        val response = request(configuration, body, headers + targetHeaders)
        return serializer.decodeFromString(responseSerializer, response)
    }
    
    private fun generateTargetHeaders(target: String): List<Header> {
        return listOf(Header("X-Amz-Target", target))
    }
}

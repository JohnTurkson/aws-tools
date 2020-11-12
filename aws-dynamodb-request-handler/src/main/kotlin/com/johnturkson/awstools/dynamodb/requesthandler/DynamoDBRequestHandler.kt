package com.johnturkson.awstools.dynamodb.requesthandler

import com.johnturkson.awstools.dynamodb.request.*
import com.johnturkson.awstools.requesthandler.AWSCredentials
import com.johnturkson.awstools.requesthandler.AWSRequestHandler
import com.johnturkson.awstools.requestsigner.AWSRequestSigner.Header
import io.ktor.client.HttpClient
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class DynamoDBRequestHandler(
    override val configuration: DynamoDBConfiguration,
    override val credentials: AWSCredentials,
    override val client: HttpClient,
    private val serializer: Json,
) : AWSRequestHandler {
    suspend fun <T> getItem(
        request: GetItemRequest<T>,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): GetItemResponse<T> {
        val target = "DynamoDB_20120810.GetItem"
        val requestSerializer = GetItemRequest.serializer(typeSerializer)
        val responseSerializer = GetItemResponse.serializer(typeSerializer)
        return request(request, headers, target, requestSerializer, responseSerializer)
    }
    
    suspend fun <T> putItem(
        request: PutItemRequest<T>,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): PutItemResponse<T> {
        val target = "DynamoDB_20120810.PutItem"
        val requestSerializer = PutItemRequest.serializer(typeSerializer)
        val responseSerializer = PutItemResponse.serializer(typeSerializer)
        return request(request, headers, target, requestSerializer, responseSerializer)
    }
    
    suspend fun <T> deleteItem(
        request: DeleteItemRequest<T>,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): DeleteItemResponse<T> {
        val target = "DynamoDB_20120810.DeleteItem"
        val requestSerializer = DeleteItemRequest.serializer(typeSerializer)
        val responseSerializer = DeleteItemResponse.serializer(typeSerializer)
        return request(request, headers, target, requestSerializer, responseSerializer)
    }
    
    suspend fun <T> query(
        request: QueryRequest<T>,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): QueryResponse<T> {
        val target = "DynamoDB_20120810.Query"
        val requestSerializer = QueryRequest.serializer(typeSerializer)
        val responseSerializer = QueryResponse.serializer(typeSerializer)
        return request(request, headers, target, requestSerializer, responseSerializer)
    }
    
    suspend fun <T> scan(
        request: ScanRequest<T>,
        typeSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): ScanResponse<T> {
        val target = "DynamoDB_20120810.Scan"
        val requestSerializer = ScanRequest.serializer(typeSerializer)
        val responseSerializer = ScanResponse.serializer(typeSerializer)
        return request(request, headers, target, requestSerializer, responseSerializer)
    }
    
    private suspend fun <T, R> request(
        request: T,
        headers: List<Header>,
        target: String,
        requestSerializer: KSerializer<T>,
        responseSerializer: KSerializer<R>,
    ): R {
        val body = serializer.encodeToString(requestSerializer, request)
        val targetHeaders = generateTargetHeaders(target)
        val credentialHeaders = generateCredentialHeaders(credentials)
        val combinedHeaders = headers + targetHeaders + credentialHeaders
        val response = request(body, combinedHeaders)
        return serializer.decodeFromString(responseSerializer, response)
    }
    
    private fun generateTargetHeaders(target: String): List<Header> {
        return listOf(Header("X-Amz-Target", target))
    }
    
    private fun generateCredentialHeaders(credentials: AWSCredentials): List<Header> {
        return when (val sessionToken = credentials.sessionToken) {
            null -> emptyList()
            else -> listOf(Header("X-Amz-Security-Token", sessionToken))
        }
    }
}

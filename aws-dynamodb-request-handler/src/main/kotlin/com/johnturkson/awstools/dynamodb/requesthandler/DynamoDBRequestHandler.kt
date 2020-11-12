package com.johnturkson.awstools.dynamodb.requesthandler

import com.johnturkson.awstools.dynamodb.request.QueryRequest
import com.johnturkson.awstools.dynamodb.request.QueryResponse
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
    suspend fun <T> query(
        request: QueryRequest<T>,
        responseSerializer: KSerializer<T>,
        headers: List<Header> = emptyList(),
    ): QueryResponse<T> {
        val body = serializer.encodeToString(QueryRequest.serializer(responseSerializer), request)
        val targetHeaders = listOf(Header("X-Amz-Target", "DynamoDB_20120810.Query"))
        val credentialHeaders = when (val sessionToken = credentials.sessionToken) {
            null -> emptyList()
            else -> listOf(Header("X-Amz-Security-Token", sessionToken))
        }
        val combinedHeaders = headers + targetHeaders + credentialHeaders
        val response = request(body, combinedHeaders)
        return serializer.decodeFromString(QueryResponse.serializer(responseSerializer), response)
    }
}

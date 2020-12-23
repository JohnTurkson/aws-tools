package com.johnturkson.awstools.qldb.client

import com.johnturkson.awstools.client.AWSClient
import com.johnturkson.awstools.client.configuration.AWSCredentials
import com.johnturkson.awstools.qldb.configuration.QLDBConfiguration
import com.johnturkson.awstools.qldb.data.CommitTransactionRequestData
import com.johnturkson.awstools.qldb.data.ExecuteStatementRequestData
import com.johnturkson.awstools.qldb.data.StartSessionRequestData
import com.johnturkson.awstools.qldb.data.StartTransactionRequestData
import com.johnturkson.awstools.qldb.errors.QLDBSessionException
import com.johnturkson.awstools.qldb.requests.CommitTransactionRequest
import com.johnturkson.awstools.qldb.requests.ExecuteStatementRequest
import com.johnturkson.awstools.qldb.requests.StartSessionRequest
import com.johnturkson.awstools.qldb.requests.StartTransactionRequest
import com.johnturkson.awstools.qldb.responses.*
import com.johnturkson.awstools.requestsigner.AWSRequestSigner.Header
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class QLDBClient(
    credentials: AWSCredentials? = null,
    region: String? = null,
    client: HttpClient? = null,
    serializer: Json? = null,
) : AWSClient(credentials, region, client, serializer) {
    
    suspend fun startSession(ledgerName: String): StartSessionResponse {
        val request = StartSessionRequest(StartSessionRequestData(ledgerName))
        return sendCommand(request)
    }
    
    suspend fun startTransaction(sessionToken: String): StartTransactionResponse {
        val request = StartTransactionRequest(sessionToken, StartTransactionRequestData)
        return sendCommand(request)
    }
    
    suspend fun commitTransaction(sessionToken: String, commitDigest: String, transactionId: String): CommitTransactionResponse {
        val request = CommitTransactionRequest(sessionToken, CommitTransactionRequestData(commitDigest, transactionId))
        return sendCommand(request)
    }
    
    suspend fun executeStatement(sessionToken: String, statement: String, transactionId: String): ExecuteStatementResponse {
        val request = ExecuteStatementRequest(sessionToken, ExecuteStatementRequestData(statement, transactionId))
        return sendCommand(request)
    }
    
    suspend fun getDigest(ledgerName: String): GetDigestResponse {
        val target = "QLDB"
        val configuration = QLDBConfiguration(endpoint = "https://qldb.$region.amazonaws.com", path = "ledgers/$ledgerName/digest", region = region)
        val body = ""
        val headers = generateHeaders(target, body)
        val (statusCode, responseBody) = makeRequest(configuration, body, headers)
        return when (statusCode) {
            200 -> serializer.decodeFromString(serializer(), responseBody)
            else -> throw serializer.decodeFromString(serializer<QLDBSessionException>(), responseBody)
        }
    }
    
    private suspend inline fun <reified T, reified R> sendCommand(request: T): R {
        val target = "QLDBSession.SendCommand"
        val configuration = QLDBConfiguration(region)
        val body = serializer.encodeToString(serializer(), request)
        val headers = generateHeaders(target, body)
        val (statusCode, responseBody) = makeRequest(configuration, body, headers)
        println(responseBody)
        return when (statusCode) {
            200 -> serializer.decodeFromString(serializer(), responseBody)
            else -> throw serializer.decodeFromString(serializer<QLDBSessionException>(), responseBody)
        }
    }
    
    private fun generateHeaders(target: String, body: String): List<Header> {
        return generateCredentialHeaders(credentials) + generateTargetHeaders(target) + generateContentHashHeaders(body)
    }
}

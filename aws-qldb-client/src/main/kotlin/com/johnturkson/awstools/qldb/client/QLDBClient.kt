package com.johnturkson.awstools.qldb.client

import com.johnturkson.awstools.client.AWSClient
import com.johnturkson.awstools.client.configuration.AWSCredentials
import com.johnturkson.awstools.qldb.configuration.QLDBConfiguration
import com.johnturkson.awstools.qldb.data.StartSessionRequestData
import com.johnturkson.awstools.qldb.data.StartTransactionRequestData
import com.johnturkson.awstools.qldb.errors.QLDBSessionException
import com.johnturkson.awstools.qldb.requests.StartSessionRequest
import com.johnturkson.awstools.qldb.requests.StartTransactionRequest
import com.johnturkson.awstools.qldb.responses.StartSessionResponse
import com.johnturkson.awstools.qldb.responses.StartTransactionResponse
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
    
    suspend fun startTransaction(ledgerName: String): StartTransactionResponse {
        val sessionToken = startSession(ledgerName).startSession.sessionToken
        val request = StartTransactionRequest(sessionToken, StartTransactionRequestData)
        return sendCommand(request)
    }
    
    private suspend inline fun <reified T, reified R> sendCommand(request: T): R {
        val target = "QLDBSession.SendCommand"
        val configuration = QLDBConfiguration(region)
        val body = serializer.encodeToString(serializer(), request)
        val headers = generateHeaders(target, body)
        val (statusCode, responseBody) = makeRequest(configuration, body, headers)
        return when (statusCode) {
            200 -> serializer.decodeFromString(serializer(), responseBody)
            else -> throw serializer.decodeFromString(serializer<QLDBSessionException>(), responseBody)
        }
    }
    
    private fun generateHeaders(target: String, body: String): List<Header> {
        return generateCredentialHeaders(credentials) + generateTargetHeaders(target) + generateContentHashHeaders(body)
    }
}

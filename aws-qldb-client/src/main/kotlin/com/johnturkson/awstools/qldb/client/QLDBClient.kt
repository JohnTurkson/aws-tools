package com.johnturkson.awstools.qldb.client

import com.johnturkson.awstools.client.AWSClient
import com.johnturkson.awstools.client.configuration.AWSCredentials
import com.johnturkson.awstools.qldb.actions.StartSession
import com.johnturkson.awstools.qldb.configuration.QLDBConfiguration
import com.johnturkson.awstools.qldb.requests.StartSessionRequest
import com.johnturkson.awstools.qldb.responses.StartSessionResponse
import com.johnturkson.awstools.requestsigner.AWSRequestSigner.Header
import io.ktor.client.*
import kotlinx.serialization.json.Json

class QLDBClient(
    credentials: AWSCredentials? = null,
    region: String? = null,
    client: HttpClient? = null,
    serializer: Json? = null,
) : AWSClient(credentials, region, client, serializer) {
    
    suspend fun startSession(ledgerName: String): StartSessionResponse {
        val target = "QLDBSession.SendCommand"
        val path = ""
        val configuration = QLDBConfiguration(region, path)
        val request = StartSession(StartSessionRequest(ledgerName))
        val body = serializer.encodeToString(StartSession.serializer(), request)
        val headers = generateHeaders(target, body)
        val (statusCode, responseBody) = makeRequest(configuration, body, headers)
        return when (statusCode) {
            200 -> serializer.decodeFromString(StartSessionResponse.serializer(), responseBody)
            else -> throw Exception()
        }
    }
    
    private fun generateHeaders(target: String, body: String): List<Header> {
        return generateCredentialHeaders(credentials) + generateTargetHeaders(target) + generateContentHashHeaders(body)
    }
}

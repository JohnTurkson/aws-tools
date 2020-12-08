package com.johnturkson.awstools.qldb.client

import com.johnturkson.awstools.client.AWSClient
import com.johnturkson.awstools.qldb.actions.StartSession
import com.johnturkson.awstools.qldb.configuration.QLDBConfiguration
import com.johnturkson.awstools.qldb.requests.StartSessionRequest
import com.johnturkson.awstools.qldb.responses.StartSessionResponse
import com.johnturkson.awstools.requesthandler.AWSCredentials
import com.johnturkson.awstools.requestsigner.AWSRequestSigner.Header
import io.ktor.client.*
import kotlinx.serialization.json.Json
import java.security.MessageDigest

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
        return generateTargetHeaders(target) + generateContentHashHeaders(body)
    }
    
    private fun generateTargetHeaders(target: String): List<Header> {
        return listOf(Header("X-Amz-Target", target))
    }
    
    private fun generateContentHashHeaders(content: String): List<Header> {
        fun ByteArray.toHex(): String {
            return StringBuilder().also { builder ->
                this.map { byte -> byte.toInt() }.forEach { bits ->
                    val shift = 0x4
                    val mask = 0xf
                    val radix = 0x10
                    val high = bits shr shift and mask
                    val low = bits and mask
                    builder.append(high.toString(radix))
                    builder.append(low.toString(radix))
                }
            }.toString()
        }
        
        fun String.hash(): String {
            val algorithm = "SHA-256"
            return MessageDigest.getInstance(algorithm).digest(this.toByteArray()).toHex()
        }
        
        return listOf(Header("X-Amz-Content-Sha256", content.hash()))
    }
}

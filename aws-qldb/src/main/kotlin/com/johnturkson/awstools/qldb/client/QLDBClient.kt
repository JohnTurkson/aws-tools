package com.johnturkson.awstools.qldb.client

import com.johnturkson.awstools.client.AWSClient
import com.johnturkson.awstools.qldb.actions.StartSession
import com.johnturkson.awstools.qldb.configuration.QLDBConfiguration
import com.johnturkson.awstools.qldb.requests.StartSessionRequest
import com.johnturkson.awstools.requesthandler.AWSCredentials
import com.johnturkson.awstools.requesthandler.AWSServiceConfiguration
import com.johnturkson.awstools.requestsigner.AWSRequestSigner.Header
import io.ktor.client.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.security.MessageDigest

class QLDBClient(
    credentials: AWSCredentials? = null,
    client: HttpClient? = null,
    region: String? = null,
    serializer: Json? = null,
) : AWSClient(credentials, region, client, serializer) {
    
    suspend fun startSession(ledgerName: String, headers: List<Header> = emptyList()): String {
        val target = "QLDBSession.SendCommand"
        val path = ""
        val configuration = QLDBConfiguration(region, path)
        val request = StartSession(StartSessionRequest(ledgerName))
        val body = serializer.encodeToString(StartSession.serializer(), request)
        val response = request(
            configuration,
            request,
            headers,
            target,
            StartSession.serializer(),
            JsonElement.serializer(),
        )
        return serializer.encodeToString(JsonElement.serializer(), response)
    }
    
    private suspend fun <T, R> request(
        configuration: AWSServiceConfiguration,
        request: T,
        headers: List<Header>,
        target: String,
        requestSerializer: KSerializer<T>,
        responseSerializer: KSerializer<R>,
    ): R {
        val body = serializer.encodeToString(requestSerializer, request)
        val targetHeaders = generateTargetHeaders(target)
        val contentHashHeaders = generateContentHashHeaders(body)
        val response = request(configuration, body, headers + targetHeaders + contentHashHeaders)
        return serializer.decodeFromString(responseSerializer, response)
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

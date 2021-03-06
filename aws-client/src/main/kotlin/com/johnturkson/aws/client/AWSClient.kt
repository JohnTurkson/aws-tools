package com.johnturkson.aws.client

import com.johnturkson.aws.client.configuration.AWSCredentials
import com.johnturkson.aws.client.configuration.AWSServiceConfiguration
import com.johnturkson.aws.client.configuration.SharedHttpClient
import com.johnturkson.aws.client.configuration.SharedJsonSerializer
import com.johnturkson.aws.requestsigner.AWSRequestSigner
import com.johnturkson.aws.requestsigner.AWSRequestSigner.Header
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.TextContent
import io.ktor.utils.io.readRemaining
import kotlinx.serialization.json.Json
import java.security.MessageDigest

abstract class AWSClient(
    credentials: AWSCredentials? = null,
    region: String? = null,
    client: HttpClient? = null,
    serializer: Json? = null,
) {
    private val credentials: AWSCredentials = credentials ?: AWSCredentials(
        System.getenv("AWS_ACCESS_KEY_ID"),
        System.getenv("AWS_SECRET_ACCESS_KEY"),
        System.getenv("AWS_SESSION_TOKEN"),
    )
    private val client: HttpClient = client ?: SharedHttpClient.instance
    val region: String = region ?: System.getenv("AWS_REGION")
    val serializer: Json = serializer ?: SharedJsonSerializer.instance
    
    suspend fun makeRequest(
        configuration: AWSServiceConfiguration,
        body: String,
        headers: List<Header>,
    ): Pair<Int, String> {
        val signedHeaders = AWSRequestSigner.generateRequestHeaders(
            credentials.accessKeyId,
            credentials.secretKey,
            configuration.region,
            configuration.service,
            configuration.method,
            configuration.url,
            body,
            headers,
        )
        val response = client.request<HttpResponse>(configuration.url) {
            this.body = TextContent(body, ContentType("application", "x-amz-json-1.0"))
            this.method = HttpMethod.parse(configuration.method)
            this.headers { signedHeaders.forEach { (name, value) -> append(name, value) } }
        }
        val statusCode = response.status.value
        val responseBody = response.content.readRemaining().readText()
        return Pair(statusCode, responseBody)
    }
    
    fun generateHeaders(target: String, body: String): List<Header> {
        return generateCredentialHeaders(credentials) + generateTargetHeaders(target) + generateContentHashHeaders(
            body)
    }
    
    fun generateCredentialHeaders(credentials: AWSCredentials): List<Header> {
        return when (val sessionToken = credentials.sessionToken) {
            null -> emptyList()
            else -> listOf(Header("X-Amz-Security-Token", sessionToken))
        }
    }
    
    fun generateTargetHeaders(target: String): List<Header> {
        return listOf(Header("X-Amz-Target", target))
    }
    
    fun generateContentHashHeaders(content: String): List<Header> {
        fun ByteArray.toHexString(): String {
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
            return MessageDigest.getInstance(algorithm).digest(this.toByteArray()).toHexString()
        }
        
        return listOf(Header("X-Amz-Content-Sha256", content.hash()))
    }
}

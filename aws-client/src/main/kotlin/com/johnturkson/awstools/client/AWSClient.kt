package com.johnturkson.awstools.client

import com.johnturkson.awstools.client.configuration.SharedHttpClient
import com.johnturkson.awstools.client.configuration.SharedJsonSerializer
import com.johnturkson.awstools.requesthandler.AWSCredentials
import com.johnturkson.awstools.requesthandler.AWSServiceConfiguration
import com.johnturkson.awstools.requestsigner.AWSRequestSigner
import com.johnturkson.awstools.requestsigner.AWSRequestSigner.Header
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.utils.io.*
import kotlinx.serialization.json.Json

abstract class AWSClient(
    credentials: AWSCredentials? = null,
    region: String? = null,
    client: HttpClient? = null,
    serializer: Json? = null,
) {
    protected val credentials: AWSCredentials = credentials ?: AWSCredentials(
        System.getenv("AWS_ACCESS_KEY_ID"),
        System.getenv("AWS_SECRET_ACCESS_KEY"),
        System.getenv("AWS_SESSION_TOKEN"),
    )
    protected val region: String = region ?: System.getenv("AWS_REGION")
    protected val client: HttpClient = client ?: SharedHttpClient.instance
    protected val serializer: Json = serializer ?: SharedJsonSerializer.instance
    
    suspend fun makeRequest(
        configuration: AWSServiceConfiguration,
        body: String,
        headers: List<Header> = emptyList(),
    ): Pair<Int, String> {
        val credentialHeaders = generateCredentialHeaders(credentials)
        val combinedHeaders = headers + credentialHeaders
        val signedHeaders = AWSRequestSigner.generateRequestHeaders(
            credentials.accessKeyId,
            credentials.secretKey,
            configuration.region,
            configuration.service,
            configuration.method,
            configuration.url,
            body,
            combinedHeaders,
        )
        val response = client.request<HttpResponse>(configuration.url) {
            this.body = TextContent(body, ContentType("application", "x-amz-json-1.0"))
            this.method = HttpMethod.parse(configuration.method)
            this.headers { signedHeaders.forEach { (name, value) -> append(name, value) } }
        }
        return response.status.value to response.content.readRemaining().readText()
    }
    
    private fun generateCredentialHeaders(credentials: AWSCredentials): List<Header> {
        return when (val sessionToken = credentials.sessionToken) {
            null -> emptyList()
            else -> listOf(Header("X-Amz-Security-Token", sessionToken))
        }
    }
}

package com.johnturkson.awstools.client

import com.johnturkson.awstools.requesthandler.AWSCredentials
import com.johnturkson.awstools.requesthandler.AWSServiceConfiguration
import com.johnturkson.awstools.requestsigner.AWSRequestSigner
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.serialization.json.Json

abstract class AWSClient(
    credentials: AWSCredentials? = null,
    region: String? = null,
    client: HttpClient? = null,
    serializer: Json? = null,
) {
    protected val credentials: AWSCredentials = credentials ?: AWSCredentials(
        System.getenv("AWS_ACCESS_KEY_ID"),
        System.getenv("AWS_SECRET_ACCCESS_KEY"),
        System.getenv("AWS_SESSION_TOKEN"),
    )
    protected val region: String = region ?: System.getenv("AWS_REGION")
    protected val client: HttpClient = client ?: HttpClient()
    protected val serializer: Json = serializer ?: Json { ignoreUnknownKeys = true }
    
    suspend fun request(
        configuration: AWSServiceConfiguration,
        body: String,
        headers: List<AWSRequestSigner.Header> = emptyList(),
    ): String {
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
            headers {
                signedHeaders.forEach { (name, value) -> append(name, value) }
            }
        }
        return response.readBytes().map(Byte::toChar).joinToString(separator = "")
    }
    
    private fun generateCredentialHeaders(credentials: AWSCredentials): List<AWSRequestSigner.Header> {
        return when (val sessionToken = credentials.sessionToken) {
            null -> emptyList()
            else -> listOf(AWSRequestSigner.Header("X-Amz-Security-Token", sessionToken))
        }
    }
}

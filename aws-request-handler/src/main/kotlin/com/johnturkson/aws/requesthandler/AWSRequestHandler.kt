package com.johnturkson.aws.requesthandler

import com.johnturkson.aws.requestsigner.AWSRequestSigner
import com.johnturkson.aws.requestsigner.AWSRequestSigner.Header
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.TextContent

interface AWSRequestHandler {
    val credentials: AWSCredentials
    val client: HttpClient
    
    suspend fun request(
        configuration: AWSServiceConfiguration,
        body: String,
        headers: List<Header> = emptyList(),
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
    
    private fun generateCredentialHeaders(credentials: AWSCredentials): List<Header> {
        return when (val sessionToken = credentials.sessionToken) {
            null -> emptyList()
            else -> listOf(Header("X-Amz-Security-Token", sessionToken))
        }
    }
}

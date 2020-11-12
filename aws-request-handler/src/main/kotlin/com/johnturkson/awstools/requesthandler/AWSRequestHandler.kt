package com.johnturkson.awstools.requesthandler

import com.johnturkson.awstools.requestsigner.AWSRequestSigner
import com.johnturkson.awstools.requestsigner.AWSRequestSigner.Header
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.TextContent

interface AWSRequestHandler {
    val credentials: AWSCredentials
    val configuration: AWSServiceConfiguration
    val client: HttpClient
    
    suspend fun request(body: String, headers: List<Header>): String {
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
            this.body = TextContent(body, ContentType.Application.Json)
            this.method = HttpMethod.parse(configuration.method)
            headers {
                signedHeaders.forEach { (name, value) -> append(name, value) }
            }
        }
        return response.readText()
    }
}

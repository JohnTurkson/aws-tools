package com.johnturkson.awstools.ses.requesthandler

import com.johnturkson.awstools.requesthandler.AWSCredentials
import com.johnturkson.awstools.requesthandler.AWSRequestHandler
import com.johnturkson.awstools.requestsigner.AWSRequestSigner.Header
import com.johnturkson.awstools.ses.requestbuilder.requests.SendEmailRequest
import com.johnturkson.awstools.ses.requestbuilder.responses.SendEmailResponse
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

class SESRequestHandler(override val client: HttpClient, private val serializer: Json) : AWSRequestHandler {
    suspend fun sendEmail(
        credentials: AWSCredentials,
        region: String,
        request: SendEmailRequest,
        headers: List<Header> = emptyList(),
    ): SendEmailResponse {
        val endpoint = "v2/email/outbound-emails"
        val configuration = SESConfiguration(region, endpoint)
        val body = serializer.encodeToString(SendEmailRequest.serializer(), request)
        val response = request(credentials, configuration, body, headers)
        return serializer.decodeFromString(SendEmailResponse.serializer(), response)
    }
    
    suspend fun sendEmail(credentials: AWSCredentials, region: String, request: String): SendEmailResponse {
        val endpoint = "v2/email/outbound-emails"
        val configuration = SESConfiguration(region, endpoint)
        val response = request(credentials, configuration, request, emptyList())
        println("response: $response")
        return serializer.decodeFromString(SendEmailResponse.serializer(), response)
    }
}

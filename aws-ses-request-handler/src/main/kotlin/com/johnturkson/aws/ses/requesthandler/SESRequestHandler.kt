package com.johnturkson.aws.ses.requesthandler

import com.johnturkson.aws.requesthandler.AWSCredentials
import com.johnturkson.aws.requesthandler.AWSRequestHandler
import com.johnturkson.aws.requestsigner.AWSRequestSigner.Header
import com.johnturkson.aws.ses.requestbuilder.requests.SendEmailRequest
import com.johnturkson.aws.ses.requestbuilder.responses.SendEmailResponse
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

class SESRequestHandler(
    override val credentials: AWSCredentials,
    override val client: HttpClient,
    private val region: String,
    private val serializer: Json,
) : AWSRequestHandler {
    suspend fun sendEmail(
        request: SendEmailRequest,
        headers: List<Header> = emptyList(),
    ): SendEmailResponse {
        val path = "v2/email/outbound-emails"
        val configuration = SESConfiguration(region, path)
        val body = serializer.encodeToString(SendEmailRequest.serializer(), request)
        val response = request(configuration, body, headers)
        return serializer.decodeFromString(SendEmailResponse.serializer(), response)
    }
}

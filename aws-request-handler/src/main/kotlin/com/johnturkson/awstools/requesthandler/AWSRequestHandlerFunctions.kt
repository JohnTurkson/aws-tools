package com.johnturkson.awstools.requesthandler

import io.ktor.client.HttpClient

fun AWSRequestHandler(
    credentials: AWSCredentials,
    configuration: AWSServiceConfiguration,
    client: HttpClient,
): AWSRequestHandler {
    return object : AWSRequestHandler {
        override val credentials: AWSCredentials = credentials
        override val configuration: AWSServiceConfiguration = configuration
        override val client: HttpClient = client
    }
}

package com.johnturkson.aws.requesthandler

import io.ktor.client.HttpClient

class DefaultAWSRequestHandler(
    override val credentials: AWSCredentials,
    override val client: HttpClient,
) : AWSRequestHandler

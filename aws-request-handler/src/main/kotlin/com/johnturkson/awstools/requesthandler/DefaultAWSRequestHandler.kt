package com.johnturkson.awstools.requesthandler

import io.ktor.client.HttpClient

class DefaultAWSRequestHandler(override val client: HttpClient) : AWSRequestHandler

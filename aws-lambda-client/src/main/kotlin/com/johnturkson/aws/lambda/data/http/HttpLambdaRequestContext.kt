package com.johnturkson.aws.lambda.data.http

import kotlinx.serialization.Serializable

@Serializable
data class HttpLambdaRequestContext(
    val accountId: String,
    val apiId: String,
    val domainName: String,
    val domainPrefix: String,
    val http: HttpLambdaRequestContextMetadata,
    val requestId: String,
    val routeKey: String,
    val stage: String,
    val time: String,
    val timeEpoch: Long,
)

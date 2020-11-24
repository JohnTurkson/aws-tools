package com.johnturkson.awstools.dynamodb.requesthandler

import com.johnturkson.awstools.requesthandler.AWSServiceConfiguration

data class DynamoDBConfiguration(
    override val region: String,
    override val path: String = "",
    override val service: String = "dynamodb",
    override val endpoint: String = "https://dynamodb.$region.amazonaws.com",
    override val url: String = "$endpoint/$path",
    override val method: String = "POST",
) : AWSServiceConfiguration(region, path, service, endpoint, url, method)

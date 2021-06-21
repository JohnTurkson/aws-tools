package com.johnturkson.aws.dynamodb.configuration

import com.johnturkson.aws.client.configuration.AWSServiceConfiguration

data class DynamoDBConfiguration(
    override val region: String,
    override val path: String = "",
    override val service: String = "dynamodb",
    override val endpoint: String = "https://dynamodb.$region.amazonaws.com",
    override val url: String = "$endpoint/$path",
    override val method: String = "POST",
) : AWSServiceConfiguration

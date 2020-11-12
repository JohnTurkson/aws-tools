package com.johnturkson.awstools.dynamodb.requesthandler

import com.johnturkson.awstools.requesthandler.AWSServiceConfiguration

data class DynamoDBConfiguration(override val region: String) : AWSServiceConfiguration {
    override val service: String = "dynamodb"
    override val url: String = "https://$service.$region.amazonaws.com"
    override val method: String = "POST"
}

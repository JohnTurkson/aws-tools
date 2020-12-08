package com.johnturkson.awstools.client.configuration

interface AWSServiceConfiguration {
    val region: String
    val path: String
    val service: String
    val endpoint: String
    val url: String
    val method: String
}

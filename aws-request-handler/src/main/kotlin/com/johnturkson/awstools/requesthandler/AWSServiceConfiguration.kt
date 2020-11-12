package com.johnturkson.awstools.requesthandler

interface AWSServiceConfiguration {
    val service: String
    val region: String
    val url: String
    val method: String
}

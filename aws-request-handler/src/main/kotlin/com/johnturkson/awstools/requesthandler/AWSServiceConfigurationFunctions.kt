package com.johnturkson.awstools.requesthandler

fun AWSServiceConfiguration(service: String, region: String, url: String, method: String): AWSServiceConfiguration {
    return object : AWSServiceConfiguration {
        override val service: String = service
        override val region: String = region
        override val url: String = url
        override val method: String = method
    }
}

package com.johnturkson.aws.requesthandler

open class AWSServiceConfiguration(
    open val region: String,
    open val path: String,
    open val service: String,
    open val endpoint: String,
    open val url: String,
    open val method: String,
)

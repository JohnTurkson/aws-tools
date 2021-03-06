package com.johnturkson.aws.ses.requesthandler

import com.johnturkson.aws.requesthandler.AWSServiceConfiguration

data class SESConfiguration(
    override val region: String,
    override val path: String,
    override val service: String = "ses",
    override val endpoint: String = "https://email.$region.amazonaws.com",
    override val url: String = "$endpoint/$path",
    override val method: String = "POST",
) : AWSServiceConfiguration(region, path, service, endpoint, url, method)

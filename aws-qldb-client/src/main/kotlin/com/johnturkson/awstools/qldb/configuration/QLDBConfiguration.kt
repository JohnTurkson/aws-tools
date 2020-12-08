package com.johnturkson.awstools.qldb.configuration

import com.johnturkson.awstools.client.configuration.AWSServiceConfiguration

data class QLDBConfiguration(
    override val region: String,
    override val path: String = "",
    override val service: String = "qldb",
    override val endpoint: String = "https://session.qldb.$region.amazonaws.com",
    override val url: String = "$endpoint/$path",
    override val method: String = "POST",
) : AWSServiceConfiguration(region, path, service, endpoint, url, method)

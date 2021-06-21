package com.johnturkson.aws.qldb.configuration

import com.johnturkson.aws.client.configuration.AWSServiceConfiguration

data class QLDBConfiguration(
    override val region: String,
    override val path: String = "",
    override val service: String = "qldb",
    override val endpoint: String = "https://session.qldb.$region.amazonaws.com",
    override val url: String = "$endpoint/$path",
    override val method: String = "POST",
) : AWSServiceConfiguration

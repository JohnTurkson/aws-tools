package com.johnturkson.aws.client.configuration

data class AWSCredentials(val accessKeyId: String, val secretKey: String, val sessionToken: String? = null)

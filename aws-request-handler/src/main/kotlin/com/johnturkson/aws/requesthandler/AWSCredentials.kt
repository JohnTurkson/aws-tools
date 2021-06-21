package com.johnturkson.aws.requesthandler

data class AWSCredentials(val accessKeyId: String, val secretKey: String, val sessionToken: String? = null)

package com.johnturkson.awstools.requesthandler

data class AWSCredentials(val accessKeyId: String, val secretKey: String, val sessionToken: String? = null)

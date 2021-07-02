package com.johnturkson.aws.lambda.client

import kotlinx.serialization.json.Json

object LambdaJsonSerializer {
    val instance by lazy {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }
}

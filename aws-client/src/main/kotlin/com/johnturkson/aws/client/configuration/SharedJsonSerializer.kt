package com.johnturkson.aws.client.configuration

import kotlinx.serialization.json.Json

object SharedJsonSerializer {
    val instance by lazy {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = false
            classDiscriminator = "__type"
        }
    }
}

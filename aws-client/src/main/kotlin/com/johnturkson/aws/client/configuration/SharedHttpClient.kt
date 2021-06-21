package com.johnturkson.aws.client.configuration

import io.ktor.client.HttpClient

object SharedHttpClient {
    val instance by lazy {
        HttpClient()
    }
}

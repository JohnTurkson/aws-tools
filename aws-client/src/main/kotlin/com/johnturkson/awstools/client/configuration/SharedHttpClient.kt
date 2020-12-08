package com.johnturkson.awstools.client.configuration

import io.ktor.client.*

object SharedHttpClient {
    val instance by lazy {
        HttpClient()
    }
}

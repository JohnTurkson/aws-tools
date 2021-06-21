package com.johnturkson.aws.lambda.extensions

import com.johnturkson.aws.lambda.data.HttpRequest
import java.util.Base64

fun HttpRequest.decodeBody(): String? {
    return when {
        body == null -> null
        isBase64Encoded -> Base64.getDecoder().decode(body).decodeToString()
        else -> body
    }
}

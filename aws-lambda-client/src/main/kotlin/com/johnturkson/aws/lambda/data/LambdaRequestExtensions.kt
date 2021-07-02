package com.johnturkson.aws.lambda.data

import com.johnturkson.aws.lambda.data.http.HttpLambdaRequest
import com.johnturkson.aws.lambda.data.websocket.WebsocketLambdaRequest
import java.util.Base64

fun HttpLambdaRequest.decodeBody(): String? {
    return when {
        body == null -> null
        isBase64Encoded -> Base64.getDecoder().decode(body).decodeToString()
        else -> body
    }
}

fun WebsocketLambdaRequest.decodeBody(): String {
    return when {
        isBase64Encoded -> Base64.getDecoder().decode(body).decodeToString()
        else -> body
    }
}

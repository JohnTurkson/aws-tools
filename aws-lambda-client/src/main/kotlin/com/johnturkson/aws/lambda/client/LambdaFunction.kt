package com.johnturkson.aws.lambda.client

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.johnturkson.aws.lambda.data.LambdaRequest
import com.johnturkson.aws.lambda.data.LambdaResponse
import com.johnturkson.aws.lambda.data.LambdaSerializer
import com.johnturkson.aws.lambda.data.decodeBody
import com.johnturkson.aws.lambda.data.http.HttpLambdaRequest
import com.johnturkson.aws.lambda.data.http.HttpLambdaResponse
import com.johnturkson.aws.lambda.data.raw.RawLambdaResponse
import com.johnturkson.aws.lambda.data.websocket.WebsocketLambdaRequest
import com.johnturkson.aws.lambda.data.websocket.WebsocketLambdaResponse
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

interface LambdaFunction<T, R> : RequestStreamHandler {
    val serializer: Json
    val requestSerializer: DeserializationStrategy<T>
    val responseSerializer: SerializationStrategy<R>
    
    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val request = input.bufferedReader().use { reader -> reader.readText() }
        val response = handle(request)
        output.bufferedWriter().use { writer -> writer.write(response) }
    }
    
    fun handle(input: String): String {
        val lambdaRequest = decodeInput(input)
        val request = decodeRequest(lambdaRequest)
        val response = processRequest(request)
        val lambdaResponse = encodeResponse(lambdaRequest, response)
        return encodeOutput(lambdaResponse)
    }
    
    fun decodeInput(input: String): LambdaRequest {
        return serializer.decodeFromString(LambdaSerializer, input)
    }
    
    fun decodeRequest(request: LambdaRequest): T? {
        val body = when (request) {
            is HttpLambdaRequest -> request.decodeBody()
            is WebsocketLambdaRequest -> request.decodeBody()
            else -> request.body
        }
        return runCatching {
            body?.let { serializer.decodeFromString(requestSerializer, it) }
        }.getOrElse {
            null
        }
    }
    
    fun processRequest(request: T?): R
    
    fun encodeRawResponse(response: R): RawLambdaResponse {
        val body = serializer.encodeToString(responseSerializer, response)
        return RawLambdaResponse(body)
    }
    
    fun encodeHttpResponse(response: R): HttpLambdaResponse {
        val body = serializer.encodeToString(responseSerializer, response)
        return HttpLambdaResponse(body)
    }
    
    fun encodeWebsocketResponse(response: R): WebsocketLambdaResponse {
        val body = serializer.encodeToString(responseSerializer, response)
        return WebsocketLambdaResponse(body)
    }
    
    fun encodeResponse(request: LambdaRequest, response: R): LambdaResponse {
        return when (request) {
            is HttpLambdaRequest -> encodeHttpResponse(response)
            is WebsocketLambdaRequest -> encodeWebsocketResponse(response)
            else -> encodeRawResponse(response)
        }
    }
    
    fun encodeOutput(response: LambdaResponse): String {
        return when (response) {
            is HttpLambdaResponse -> serializer.encodeToString(HttpLambdaResponse.serializer(), response)
            is WebsocketLambdaResponse -> response.body
            else -> response.body
        }
    }
}

package com.johnturkson.aws.lambda.client

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.johnturkson.aws.lambda.data.HttpRequest
import com.johnturkson.aws.lambda.data.HttpResponse
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

interface HttpLambdaFunction<T, R> : RequestStreamHandler {
    val serializer: Json
    
    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val request = input.bufferedReader().use { reader -> reader.readText() }
        val response = handleInput(request)
        output.bufferedWriter().use { writer -> writer.write(response) }
    }
    
    fun handleInput(input: String): String {
        val httpRequest = runCatching {
            decodeHttpRequest(input)
        }.getOrElse { exception ->
            val response = onInvalidHttpRequest(input, exception)
            return encodeHttpResponse(response)
        }
        
        val httpResponse = handleHttpRequest(httpRequest)
        return encodeHttpResponse(httpResponse)
    }
    
    fun handleHttpRequest(httpRequest: HttpRequest): HttpResponse {
        runCatching {
            verifyAuthorization(httpRequest)
        }.getOrElse { exception ->
            return onInvalidAuthorization(httpRequest, exception)
        }
        
        val typedRequest = runCatching {
            decodeTypedRequest(httpRequest)
        }.getOrElse { exception ->
            return onInvalidTypedRequest(httpRequest, exception)
        }
        
        val typedResponse = processTypedRequest(typedRequest)
        return encodeTypedResponse(typedResponse)
    }
    
    fun decodeHttpRequest(input: String): HttpRequest {
        return serializer.decodeFromString(HttpRequest.serializer(), input)
    }
    
    fun onInvalidHttpRequest(input: String, exception: Throwable): HttpResponse
    
    fun verifyAuthorization(request: HttpRequest)
    
    fun onInvalidAuthorization(request: HttpRequest, exception: Throwable): HttpResponse
    
    fun decodeTypedRequest(request: HttpRequest): T
    
    fun onInvalidTypedRequest(request: HttpRequest, exception: Throwable): HttpResponse
    
    fun processTypedRequest(request: T): R
    
    fun encodeTypedResponse(response: R): HttpResponse
    
    fun encodeHttpResponse(response: HttpResponse): String {
        return serializer.encodeToString(HttpResponse.serializer(), response)
    }
}

package com.johnturkson.awstools.lambda.data

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64

interface HttpLambdaFunction<T, R> : RequestStreamHandler {
    val serializer: Json
    
    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val request = input.bufferedReader().use { reader -> reader.readText() }
        val response = handle(request)
        output.bufferedWriter().use { writer -> writer.write(response) }
    }
    
    fun handle(input: String): String {
        val httpRequest = runCatching {
            decodeHttpRequest(input)
        }.getOrElse { exception ->
            val response = onInvalidHttpRequest(input, exception)
            return encodeHttpResponse(response)
        }
        
        runCatching {
            verifyAuthorization(httpRequest)
        }.getOrElse { exception ->
            val response = onInvalidAuthorization(httpRequest, exception)
            return encodeHttpResponse(response)
        }
        
        val typedRequest = runCatching {
            decodeTypedRequest(httpRequest)
        }.getOrElse { exception ->
            val response = onInvalidTypedRequest(httpRequest, exception)
            return encodeHttpResponse(response)
        }
        
        val typedResponse = processTypedRequest(typedRequest)
        val httpResponse = encodeTypedResponse(typedResponse)
        return encodeHttpResponse(httpResponse)
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
    
    fun HttpRequest.decodeBody(): String? {
        return when {
            body == null -> null
            isBase64Encoded -> Base64.getDecoder().decode(body).decodeToString()
            else -> body
        }
    }
}

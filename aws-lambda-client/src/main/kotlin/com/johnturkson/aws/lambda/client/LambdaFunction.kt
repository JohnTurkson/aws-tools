package com.johnturkson.aws.lambda.client

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.johnturkson.aws.lambda.v2.LambdaRequest
import com.johnturkson.aws.lambda.v2.LambdaRequest.*
import com.johnturkson.aws.lambda.v2.LambdaResponse
import com.johnturkson.aws.lambda.v2.LambdaResponse.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

interface LambdaFunction<T, R> : RequestStreamHandler {
    val serializer: Json
    val requestSerializer: KSerializer<T>
    val responseSerializer: KSerializer<R>
    
    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val request = input.bufferedReader().use { reader -> reader.readText() }
        val response = handle(request)
        output.bufferedWriter().use { writer -> writer.write(response) }
    }
    
    fun handle(input: String): String {
        // TODO attempt to get context
        
        // TODO parse directly to LambdaRequest
        val request = runCatching {
            decodeInput(input)
        }.getOrElse { exception ->
            val response = onError(input, exception)
            return encodeOutput(response)
        }
        
        val re = decodeRequest() // TODO
        val res = processRequest(re)
        
        val response = encodeResponse(request, res)
        
        return encodeOutput(response)
    }
    
    fun decodeInput(input: String): LambdaRequest {
        // json content polymorphic serialization
        
        // return serializer.decodeFromString(requestSerializer, input)
        TODO()
    }
    
    fun decodeRequest(body: String?): T {
        TODO()
    }
    
    // unknown input type (could not be coerced)
    fun onError(input: String, exception: Throwable): LambdaResponse
    
    fun processRequest(request: T): R
    
    fun encodeRawResponse(response: R): RawLambdaResponse {
        val body = serializer.encodeToString(responseSerializer, response)
        return RawLambdaResponse(body)
    }
    
    fun encodeHttpResponse(response: R): HttpLambdaResponse {
        throw NotImplementedError()
    }
    
    fun encodeWebsocketResponse(response: R): WebsocketLambdaResponse {
        throw NotImplementedError()
    }
    
    fun encodeResponse(request: LambdaRequest, response: R): LambdaResponse {
        return when (request) {
            is RawLambdaRequest -> encodeRawResponse(response)
            is HttpLambdaRequest -> encodeHttpResponse(response)
            is WebsocketLambdaRequest -> encodeWebsocketResponse(response)
        }
    }
    
    fun encodeOutput(response: LambdaResponse): String {
        return when (response) {
            is RawLambdaResponse -> response.body
            is HttpLambdaResponse -> serializer.encodeToString(HttpLambdaResponse.serializer(), response)
            is WebsocketLambdaResponse -> response.body
        }
    }
}

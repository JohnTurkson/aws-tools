package com.johnturkson.aws.lambda.client

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
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
        val request = runCatching {
            decodeInput(input)
        }.getOrElse { exception ->
            val response = handleInvalidInput(input, exception)
            return encodeOutput(response)
        }
        
        val response = handleRequest(request)
        return encodeOutput(response)
    }
    
    fun handleRequest(request: T): R {
        runCatching {
            checkRequestAuthorization(request)
        }.getOrElse { exception ->
            return handleInvalidRequestAuthorization(request, exception)
        }
        
        return processRequest(request)
    }
    
    fun decodeInput(input: String): T {
        return serializer.decodeFromString(requestSerializer, input)
    }
    
    fun handleInvalidInput(input: String, exception: Throwable): R
    
    fun checkRequestAuthorization(request: T)
    
    fun handleInvalidRequestAuthorization(request: T, exception: Throwable): R
    
    fun processRequest(request: T): R
    
    fun encodeOutput(response: R): String {
        return serializer.encodeToString(responseSerializer, response)
    }
}

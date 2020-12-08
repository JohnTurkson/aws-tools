package com.johnturkson.awstools.requestsigner

import java.net.URL
import java.security.MessageDigest
import java.time.Instant
import java.time.ZoneOffset
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object AWSRequestSigner {
    fun generateRequestHeaders(
        accessKeyId: String,
        secretKey: String,
        region: String,
        service: String,
        method: String,
        url: String,
        body: String = "",
        headers: List<Header> = emptyList(),
    ): List<Header> {
        val (date, time) = generateDateTime()
        val dateHeader = generateDateHeader(date, time)
        val hostHeader = generateHostHeader(url)
        
        val canonicalHeaders = generateCanonicalHeaders(headers + dateHeader + hostHeader)
        val signedHeaders = generateSignedHeaders(headers + dateHeader + hostHeader)
        val hashedPayload = computeSHA256(body.toByteArray()).toHex()
        
        val canonicalUri = generateCanonicalUri(url)
        val canonicalQueryString = generateCanonicalQueryString(url)
        val canonicalRequest = generateCanonicalRequest(
            method,
            canonicalUri,
            canonicalQueryString,
            canonicalHeaders,
            signedHeaders,
            hashedPayload
        )
        val canonicalRequestHash = generateCanonicalRequestHash(canonicalRequest)
        
        val credentialScope = generateCredentialScope(date, region, service)
        val stringToSign = generateStringToSign(dateHeader.value, credentialScope, canonicalRequestHash)
        val signature = generateSignature(secretKey, date, region, service, stringToSign)
        
        val authorizationHeader = generateAuthorizationHeader(accessKeyId, credentialScope, signedHeaders, signature)
        
        println(authorizationHeader)
        
        return headers + dateHeader + hostHeader + authorizationHeader
    }
    
    private fun generateAuthorizationHeader(
        accessKeyId: String,
        credentialScope: String,
        signedHeaders: String,
        signature: String,
    ): Header {
        val authorizationHeaderName = "Authorization"
        val authorizationHeaderValue = generateAuthorizationHeaderValue(
            accessKeyId,
            credentialScope,
            signedHeaders,
            signature
        )
        return Header(authorizationHeaderName, authorizationHeaderValue)
    }
    
    private fun generateAuthorizationHeaderValue(
        accessKeyId: String,
        credentialScope: String,
        signedHeaders: String,
        signature: String,
    ): String {
        val algorithm = "AWS4-HMAC-SHA256"
        val authorizationCredential = "Credential=$accessKeyId/$credentialScope"
        val authorizationSignedHeaders = "SignedHeaders=$signedHeaders"
        val authorizationSignature = "Signature=$signature"
        return "$algorithm $authorizationCredential, $authorizationSignedHeaders, $authorizationSignature"
    }
    
    private fun generateCanonicalUri(url: String): String {
        val emptyCanonicalUri = "/"
        // TODO encode url twice
        return encodeUrl(URL(url).path).ifEmpty { emptyCanonicalUri }
    }
    
    private fun encodeUrl(url: String): String {
        // TODO
        return url
            .replace("%", "%25")
    }
    
    private fun generateCanonicalQueryString(query: String): String {
        // TODO
        return ""
    }
    
    private fun generateDateHeader(date: String, time: String): Header {
        val dateHeaderName = "X-Amz-Date"
        val dateHeaderValue = generateDateHeaderValue(date, time)
        return Header(dateHeaderName, dateHeaderValue)
    }
    
    private fun generateDateHeaderValue(date: String, time: String): String {
        val separator = "T"
        val termination = "Z"
        return date + separator + time + termination
    }
    
    private fun generateDateTime(
        instant: Instant = Instant.now(),
        offset: ZoneOffset = ZoneOffset.UTC,
    ): Pair<String, String> {
        return with(instant.atOffset(offset)) {
            val year = year.toString().padStart(4, '0')
            val month = monthValue.toString().padStart(2, '0')
            val day = dayOfMonth.toString().padStart(2, '0')
            
            val hour = hour.toString().padStart(2, '0')
            val minute = minute.toString().padStart(2, '0')
            val second = second.toString().padStart(2, '0')
            
            val date = year + month + day
            val time = hour + minute + second
            
            Pair(date, time)
        }
    }
    
    private fun generateHostHeader(url: String): Header {
        val hostHeaderName = "Host"
        val hostHeaderValue = generateHostHeaderValue(url)
        return Header(hostHeaderName, hostHeaderValue)
    }
    
    private fun generateHostHeaderValue(url: String): String {
        return URL(url).host
    }
    
    private fun generateCanonicalHeaders(headers: List<Header>): String {
        return headers.asSequence()
            .map { (name, value) -> name to value }
            .groupBy { (name, _) -> name.toLowerCase() }
            .mapValues { (_, values) -> values.map { (_, value) -> value } }
            .map { (name, values) -> name to values }
            .sortedBy { (name, _) -> name }
            .map { (name, values) -> name to values.joinToString(separator = ",") }
            .joinToString(separator = "") { (name, values) -> "$name:$values\n" }
    }
    
    private fun generateSignedHeaders(headers: List<Header>): String {
        return headers.asSequence()
            .map { (name, value) -> name to value }
            .map { (name, _) -> name.toLowerCase() }
            .distinct()
            .sorted()
            .joinToString(separator = ";")
    }
    
    private fun generateCanonicalRequest(
        requestMethod: String,
        canonicalUri: String,
        canonicalQueryString: String,
        canonicalHeaders: String,
        signedHeaders: String,
        hashedPayload: String,
    ): String {
        return "$requestMethod\n$canonicalUri\n$canonicalQueryString\n$canonicalHeaders\n$signedHeaders\n$hashedPayload"
    }
    
    private fun generateCanonicalRequestHash(canonicalRequest: String): String {
        return hash(canonicalRequest)
    }
    
    private fun hash(data: String): String {
        return computeSHA256(data.toByteArray()).toHex()
    }
    
    private fun generateCredentialScope(date: String, region: String, service: String): String {
        val termination = "aws4_request"
        return "$date/$region/$service/$termination"
    }
    
    private fun generateStringToSign(
        date: String,
        credentialScope: String,
        canonicalRequestHash: String,
    ): String {
        val algorithm = "AWS4-HMAC-SHA256"
        return "$algorithm\n$date\n$credentialScope\n$canonicalRequestHash"
    }
    
    private fun generateSignature(
        secretKey: String,
        date: String,
        region: String,
        service: String,
        stringToSign: String,
    ): String {
        val prefix = "AWS4"
        val termination = "aws4_request"
        val signingDate = sign(prefix + secretKey, date)
        val signingRegion = sign(signingDate, region)
        val signingService = sign(signingRegion, service)
        val signingKey = sign(signingService, termination)
        val signature = sign(signingKey, stringToSign)
        return signature.toHex()
    }
    
    private fun sign(key: String, data: String): ByteArray {
        return computeHMACSHA256(key.toByteArray(), data.toByteArray())
    }
    
    private fun sign(key: ByteArray, data: String): ByteArray {
        return computeHMACSHA256(key, data.toByteArray())
    }
    
    private fun computeSHA256(data: ByteArray): ByteArray {
        val algorithm = "SHA-256"
        return MessageDigest.getInstance(algorithm).digest(data)
    }
    
    private fun computeHMACSHA256(key: ByteArray, data: ByteArray): ByteArray {
        val algorithm = "HmacSHA256"
        return Mac.getInstance(algorithm).apply {
            init(SecretKeySpec(key, algorithm))
        }.doFinal(data)
    }
    
    private fun ByteArray.toHex(): String {
        return StringBuilder().also { builder ->
            this.map { byte -> byte.toInt() }.forEach { bits ->
                val shift = 0x4
                val mask = 0xf
                val radix = 0x10
                val high = bits shr shift and mask
                val low = bits and mask
                builder.append(high.toString(radix))
                builder.append(low.toString(radix))
            }
        }.toString()
    }
    
    data class Header(val name: String, val value: String)
}

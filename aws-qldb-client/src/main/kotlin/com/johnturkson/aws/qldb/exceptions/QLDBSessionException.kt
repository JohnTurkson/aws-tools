package com.johnturkson.aws.qldb.exceptions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class QLDBSessionException : Exception() {
    @SerialName("Message")
    abstract override val message: String
    
    @Serializable
    @SerialName("com.amazonaws.qldb.session#BadRequestException")
    data class BadRequestException(@SerialName("Message") override val message: String) : com.johnturkson.aws.qldb.exceptions.QLDBSessionException()
    
    @Serializable
    @SerialName("com.amazonaws.qldb.session#InvalidSessionException")
    data class InvalidSessionException(@SerialName("Message") override val message: String) : com.johnturkson.aws.qldb.exceptions.QLDBSessionException()
    
    @Serializable
    @SerialName("com.amazonaws.qldb.session#LimitExceededException")
    data class LimitExceededException(@SerialName("Message") override val message: String) : com.johnturkson.aws.qldb.exceptions.QLDBSessionException()
    
    @Serializable
    @SerialName("com.amazonaws.qldb.session#OccConflictException")
    data class OccConflictException(@SerialName("Message") override val message: String) : com.johnturkson.aws.qldb.exceptions.QLDBSessionException()
    
    @Serializable
    @SerialName("com.amazonaws.qldb.session#RateExceededException")
    data class RateExceededException(@SerialName("Message") override val message: String) : com.johnturkson.aws.qldb.exceptions.QLDBSessionException()
}

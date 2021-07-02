package com.johnturkson.aws.lambda.data.raw

import com.johnturkson.aws.lambda.data.LambdaResponse
import kotlinx.serialization.Serializable

@Serializable
data class RawLambdaResponse(override val body: String) : LambdaResponse

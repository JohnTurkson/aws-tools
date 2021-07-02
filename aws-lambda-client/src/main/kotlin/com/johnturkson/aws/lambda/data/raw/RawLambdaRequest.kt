package com.johnturkson.aws.lambda.data.raw

import com.johnturkson.aws.lambda.data.LambdaRequest
import kotlinx.serialization.Serializable

@Serializable
data class RawLambdaRequest(override val body: String) : LambdaRequest

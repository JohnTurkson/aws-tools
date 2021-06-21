package com.johnturkson.aws.dynamodb.data

import com.johnturkson.aws.dynamodb.serializers.ProjectionExpressionSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ProjectionExpressionSerializer::class)
data class ProjectionExpression(val attributes: List<String>)

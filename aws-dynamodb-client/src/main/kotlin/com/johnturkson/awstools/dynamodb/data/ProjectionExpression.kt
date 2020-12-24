package com.johnturkson.awstools.dynamodb.data

import com.johnturkson.awstools.dynamodb.serializers.ProjectionExpressionSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ProjectionExpressionSerializer::class)
data class ProjectionExpression(val attributes: List<String>)

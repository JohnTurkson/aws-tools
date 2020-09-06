package com.johnturkson.awstools.dynamodb.request.components

import com.johnturkson.awstools.dynamodb.request.serializers.ProjectionExpressionSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ProjectionExpressionSerializer::class)
data class ProjectionExpression(val attributes: List<String>)

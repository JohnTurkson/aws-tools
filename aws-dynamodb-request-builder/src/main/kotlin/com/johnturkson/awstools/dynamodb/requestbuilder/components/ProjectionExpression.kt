package com.johnturkson.awstools.dynamodb.requestbuilder.components

import com.johnturkson.awstools.dynamodb.requestbuilder.serializers.ProjectionExpressionSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ProjectionExpressionSerializer::class)
data class ProjectionExpression(val attributes: List<String>)

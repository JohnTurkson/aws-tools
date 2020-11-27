package com.johnturkson.awstools.dynamodb.requestbuilder.data

import com.johnturkson.awstools.dynamodb.requestbuilder.serializers.ProjectionExpressionSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ProjectionExpressionSerializer::class)
data class ProjectionExpression(val attributes: List<String>)

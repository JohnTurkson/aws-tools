package com.johnturkson.awstools.dynamodb.objectbuilder

fun buildDynamoDBObject(builder: DynamoDBObjectBuilder.() -> Unit): DynamoDBObject {
    return DynamoDBObjectBuilder().apply { builder() }.build()
}

fun buildDynamoDBList(builder: DynamoDBListBuilder.() -> Unit): DynamoDBList {
    return DynamoDBListBuilder().apply { builder() }.build()
}

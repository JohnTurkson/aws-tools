package com.johnturkson.aws.ses.requestbuilder.dsl

import com.johnturkson.aws.ses.requestbuilder.builder.DestinationBuilder
import com.johnturkson.aws.ses.requestbuilder.components.Destination

fun Destination(build: DestinationBuilder.() -> DestinationBuilder): Destination {
    return DestinationBuilder().build().getData()
}

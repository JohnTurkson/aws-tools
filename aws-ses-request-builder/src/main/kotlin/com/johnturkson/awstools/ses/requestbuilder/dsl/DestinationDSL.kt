package com.johnturkson.awstools.ses.requestbuilder.builder

import com.johnturkson.awstools.ses.requestbuilder.components.Destination

fun Destination(build: DestinationBuilder.() -> DestinationBuilder): Destination {
    return DestinationBuilder().build().getData()
}

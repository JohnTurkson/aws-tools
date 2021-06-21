package com.johnturkson.aws.ses.requestbuilder.dsl

import com.johnturkson.aws.ses.requestbuilder.builder.EmailContentBuilder
import com.johnturkson.aws.ses.requestbuilder.components.EmailContent

fun Content(build: EmailContentBuilder.() -> EmailContentBuilder): EmailContent {
    return EmailContentBuilder().build().getData()
}

package com.johnturkson.awstools.ses.requestbuilder.builder

import com.johnturkson.awstools.ses.requestbuilder.components.EmailContent

fun Content(build: EmailContentBuilder.() -> EmailContentBuilder): EmailContent {
    return EmailContentBuilder().build().getData()
}

package com.johnturkson.aws.ses.requestbuilder.builder

import com.johnturkson.aws.ses.requestbuilder.components.Body
import com.johnturkson.aws.ses.requestbuilder.components.Content
import com.johnturkson.aws.ses.requestbuilder.components.Message

class MessageBuilder(var body: Body? = null, var subject: Content? = null) {
    fun getData(): Message {
        val body = this.body
        val subject = this.subject
        
        return when {
            body == null -> throw Exception("Missing message data")
            subject == null -> throw Exception("Missing subject data")
            else -> Message(body, subject)
        }
    }
    
    fun Body(build: BodyBuilder.() -> BodyBuilder): MessageBuilder {
        this.body = BodyBuilder().build().getData()
        return this
    }
    
    fun Subject(build: SubjectBuilder.() -> SubjectBuilder): MessageBuilder {
        this.subject = SubjectBuilder().build().getData()
        return this
    }
}

package com.johnturkson.aws.ses.requestbuilder.builder

import com.johnturkson.aws.ses.requestbuilder.components.EmailContent
import com.johnturkson.aws.ses.requestbuilder.components.EmailContent.*
import com.johnturkson.aws.ses.requestbuilder.components.Message
import com.johnturkson.aws.ses.requestbuilder.components.RawMessage
import com.johnturkson.aws.ses.requestbuilder.components.Template

class EmailContentBuilder(var raw: RawMessage? = null, var simple: Message? = null, var template: Template? = null) {
    fun getData(): EmailContent {
        val raw = this.raw
        val simple = this.simple
        val template = this.template
        val data = listOfNotNull(raw, simple, template)
        
        return when {
            data.count() > 1 -> throw Exception("Duplicate Raw, Simple, or Template data")
            raw != null -> RawEmailContent(raw)
            simple != null -> SimpleEmailContent(simple)
            template != null -> TemplateEmailContent(template)
            else -> throw Exception("Missing Raw, Simple, or Template data")
        }
    }
    
    fun Raw(build: RawMessageBuilder.() -> RawMessageBuilder): EmailContentBuilder {
        this.raw = RawMessageBuilder().build().getData()
        return this
    }
    
    fun Simple(build: MessageBuilder.() -> MessageBuilder): EmailContentBuilder {
        this.simple = MessageBuilder().build().getData()
        return this
    }
    
    fun Template(build: TemplateBuilder.() -> TemplateBuilder): EmailContentBuilder {
        this.template = TemplateBuilder().build().getData()
        return this
    }
}

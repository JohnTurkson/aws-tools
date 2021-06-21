package com.johnturkson.aws.ses.requestbuilder.builder

import com.johnturkson.aws.ses.requestbuilder.components.Body
import com.johnturkson.aws.ses.requestbuilder.components.Body.HtmlBody
import com.johnturkson.aws.ses.requestbuilder.components.Body.TextBody
import com.johnturkson.aws.ses.requestbuilder.components.Content

class BodyBuilder(var text: Content? = null, var html: Content? = null) {
    fun getData(): Body {
        val text = this.text
        val html = this.html
        val data = listOfNotNull(text, html)
        
        return when {
            data.count() > 1 -> throw Exception("Repeated Text or Html data")
            text != null -> TextBody(text)
            html != null -> HtmlBody(html)
            else -> throw Exception("Missing Text or Html data")
        }
    }
    
    fun Text(build: TextContentBuilder.() -> TextContentBuilder): BodyBuilder {
        this.text = TextContentBuilder().build().getData()
        return this
    }
    
    fun Html(build: HtmlContentBuilder.() -> HtmlContentBuilder): BodyBuilder {
        this.html = HtmlContentBuilder().build().getData()
        return this
    }
}

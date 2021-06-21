package com.johnturkson.aws.ses.requestbuilder.builder

import com.johnturkson.aws.ses.requestbuilder.components.Content

class HtmlContentBuilder(var data: String? = null, var charset: String? = null) {
    fun getData(): Content {
        return Content(data ?: throw Exception("Missing content data"), charset)
    }
    
    fun Data(data: String): HtmlContentBuilder {
        this.data = data
        return this
    }
    
    fun Charset(charset: String): HtmlContentBuilder {
        this.charset = charset
        return this
    }
}


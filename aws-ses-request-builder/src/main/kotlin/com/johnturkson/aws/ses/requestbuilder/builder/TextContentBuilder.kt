package com.johnturkson.aws.ses.requestbuilder.builder

import com.johnturkson.aws.ses.requestbuilder.components.Content

class TextContentBuilder(var data: String? = null, var charset: String? = null) {
    fun getData(): Content {
        return Content(data ?: throw Exception("Missing Content data"), charset)
    }
    
    fun Data(data: String): TextContentBuilder {
        this.data = data
        return this
    }
    
    fun Charset(charset: String): TextContentBuilder {
        this.charset = charset
        return this
    }
}

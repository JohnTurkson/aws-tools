package com.johnturkson.awstools.ses.requestbuilder.builder

import com.johnturkson.awstools.ses.requestbuilder.components.Content

class SubjectBuilder(var data: String? = null, var charset: String? = null) {
    fun getData(): Content {
        return Content(data ?: throw Exception("Missing content data"), charset)
    }
    
    fun Data(data: String): SubjectBuilder {
        this.data = data
        return this
    }
    
    fun Charset(charset: String?): SubjectBuilder {
        this.charset = charset
        return this
    }
    
}

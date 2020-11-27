package com.johnturkson.awstools.ses.requestbuilder.builder

import com.johnturkson.awstools.ses.requestbuilder.components.RawMessage

class RawMessageBuilder(var data: String? = null) {
    fun getData(): RawMessage {
        return when (val data = this.data) {
            null -> throw Exception("Missing message data")
            else -> RawMessage(data)
        }
    }
    
    fun Data(data: String): RawMessageBuilder {
        this.data = data
        return this
    }
}

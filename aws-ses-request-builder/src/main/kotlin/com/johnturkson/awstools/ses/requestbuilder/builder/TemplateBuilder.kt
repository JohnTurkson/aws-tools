package com.johnturkson.awstools.ses.requestbuilder.builder

import com.johnturkson.awstools.ses.requestbuilder.components.Template

class TemplateBuilder(var arn: String? = null, var data: String? = null, var name: String? = null) {
    fun getData(): Template {
        val arn = this.arn
        val data = this.data
        val name = this.name
        
        require(arn != null) { "Missing TemplateArn data" }
        require(data != null) { "Missing TemplateData data" }
        require(name != null) { "Missing TemplateName data" }
        
        return Template(arn, data, name)
    }
    
    fun TemplateArn(arn: String): TemplateBuilder {
        this.arn = arn
        return this
    }
    
    fun TemplateData(data: String): TemplateBuilder {
        this.data = data
        return this
    }
    
    fun TemplateName(name: String): TemplateBuilder {
        this.name = name
        return this
    }
    
}


package com.johnturkson.awstools.ses.requestbuilder.builder

import com.johnturkson.awstools.ses.requestbuilder.components.Destination

class DestinationBuilder(
    var to: List<String> = emptyList(),
    var cc: List<String> = emptyList(),
    var bcc: List<String> = emptyList(),
) {
    fun getData(): Destination {
        val to = this.to
        val cc = this.cc
        val bcc = this.bcc
        require(to.isNotEmpty()) { "Missing ToAddresses data" }
        return Destination(to, cc, bcc)
    }
    
    fun ToAddresses(vararg to: String): DestinationBuilder {
        this.to = to.toList()
        return this
    }
    
    fun ToAddresses(to: List<String>): DestinationBuilder {
        this.to = to
        return this
    }
    
    fun CcAddresses(vararg cc: String): DestinationBuilder {
        this.cc = cc.toList()
        return this
    }
    
    fun CcAddresses(cc: List<String>): DestinationBuilder {
        this.cc = cc
        return this
    }
    
    fun BccAddresses(vararg bcc: String): DestinationBuilder {
        this.bcc = bcc.toList()
        return this
    }
    
    fun BccAddresses(bcc: List<String>): DestinationBuilder {
        this.bcc = bcc
        return this
    }
}

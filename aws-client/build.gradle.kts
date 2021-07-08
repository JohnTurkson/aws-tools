plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib"))
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    api("io.ktor:ktor-client-core:1.6.1")
    api("io.ktor:ktor-client-cio:1.6.1")
    api(project(":aws-request-signer"))
}

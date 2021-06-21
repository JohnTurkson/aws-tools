plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
    implementation("io.ktor:ktor-client-core:1.6.0")
    implementation("io.ktor:ktor-client-cio:1.6.0")
    implementation(project(":aws-request-signer"))
}

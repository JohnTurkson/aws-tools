plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-client-core:1.6.1")
    implementation(project(":aws-request-signer"))
}

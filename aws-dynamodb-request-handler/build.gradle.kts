plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("io.ktor:ktor-client-core:1.4.2")
    implementation(project(":aws-dynamodb-request-builder"))
    implementation(project(":aws-request-handler"))
    implementation(project(":aws-request-signer"))
}

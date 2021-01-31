plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation("io.ktor:ktor-client-core:1.5.0")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation(project(":aws-request-signer"))
    implementation(project(":aws-client"))
}

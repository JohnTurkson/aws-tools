plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
    implementation("io.ktor:ktor-client-core:1.6.1")
    implementation(project(":aws-ses-request-builder"))
    implementation(project(":aws-request-handler"))
    implementation(project(":aws-request-signer"))
}

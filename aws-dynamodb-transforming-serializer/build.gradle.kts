plugins {
    kotlin("jvm")
}

group = "com.johnturkson.aws-tools"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")
    implementation(project(":aws-dynamodb-object-builder"))
}

tasks {
    jar {
        configurations.runtimeClasspath.get().forEach { file -> from(zipTree(file.absoluteFile)) }
    }
}

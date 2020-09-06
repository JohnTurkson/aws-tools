plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "com.johnturkson.aws-tools"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")
}

tasks {
    jar {
        configurations.runtimeClasspath.get().forEach { file -> from(zipTree(file.absoluteFile)) }
    }
}

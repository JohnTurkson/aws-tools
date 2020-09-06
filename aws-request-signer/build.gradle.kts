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
}

tasks {
    jar {
        configurations.runtimeClasspath.get().forEach { file -> from(zipTree(file.absoluteFile)) }
    }
}

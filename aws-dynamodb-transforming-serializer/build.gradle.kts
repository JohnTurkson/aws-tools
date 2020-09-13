plugins {
    kotlin("jvm") version "1.4.10"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")
    implementation(project(":aws-dynamodb-object-builder"))
}

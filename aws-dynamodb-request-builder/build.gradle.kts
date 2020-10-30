plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
    implementation(project(":aws-dynamodb-object-builder"))
    implementation(project(":aws-dynamodb-transforming-serializer"))
}

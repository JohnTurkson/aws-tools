plugins {
    kotlin("jvm") version "1.5.20" apply false
    kotlin("plugin.serialization") version "1.5.20" apply false
    `java-library`
    `maven-publish`
}

allprojects {
    group = "com.johnturkson.aws"
    version = "0.0.11"
    
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    
    java {
        withJavadocJar()
        withSourcesJar()
    }
    
    publishing {
        publications {
            create<MavenPublication>("maven") {
                pom {
                    name.set(project.name)
                    description.set("Kotlin-first utilities and tools for interacting with various AWS services without requiring the AWS SDK.")
                    url.set("https://www.github.com/JohnTurkson/aws-tools")
                    
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    
                    developers {
                        developer {
                            id.set("JohnTurkson")
                            name.set("John Turkson")
                            email.set("johnturkson@johnturkson.com")
                            url.set("https://www.johnturkson.com")
                        }
                    }
                    
                    scm {
                        connection.set("scm:git:git://github.com/JohnTurkson/aws-tools.git")
                        developerConnection.set("scm:git:ssh://git.jetbrains.space/johnturkson/aws/aws-tools.git")
                        url.set("https://github.com/JohnTurkson/aws-tools")
                    }
                }
                
                afterEvaluate {
                    from(components["java"])
                }
            }
        }
        
        repositories {
            maven {
                name = "GitLabPackages"
                url = uri("https://gitlab.com/api/v4/projects/${System.getenv("GITLAB_PROJECT_ID")}/packages/maven")
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
                credentials(HttpHeaderCredentials::class) {
                    name = "Deploy-Token"
                    value = System.getenv("GITLAB_PUBLISHING_TOKEN")
                }
            }
        }
    }
}

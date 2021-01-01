plugins {
    kotlin("jvm") version "1.4.21" apply false
    kotlin("plugin.serialization") version "1.4.21" apply false
    `maven-publish`
}

allprojects {
    group = "com.johnturkson.aws-tools"
    version = "0.0.41"
    
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "maven-publish")
    
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
                    from(components["kotlin"])
                }
            }
        }
        
        repositories {
            maven {
                name = "SpacePackages"
                url = uri("https://maven.pkg.jetbrains.space/johnturkson/p/packages/public")
                credentials {
                    username = System.getenv("SPACE_PUBLISHING_USERNAME")
                    password = System.getenv("SPACE_PUBLISHING_TOKEN")
                }
            }
            
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/johnturkson/aws-tools")
                credentials {
                    username = System.getenv("GITHUB_PUBLISHING_USERNAME")
                    password = System.getenv("GITHUB_PUBLISHING_TOKEN")
                }
            }
        }
    }
}

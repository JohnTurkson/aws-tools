plugins {
    kotlin("jvm") version "1.4.10" apply false
    kotlin("plugin.serialization") version "1.4.10" apply false
    `maven-publish`
}

allprojects {
    group = "com.johnturkson.aws-tools"
    version = "0.0.28"
    
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "maven-publish")
    
    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/johnturkson/aws-tools")
                credentials {
                    username = System.getenv("GITHUB_USERNAME")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
            
            maven {
                name = "SpacePackages"
                url = uri("https://maven.pkg.jetbrains.space/johnturkson/p/aws/aws-tools")
                credentials {
                    username = System.getenv("SPACE_USERNAME")
                    password = System.getenv("SPACE_TOKEN")
                }
            }
        }
        
        publications {
            create<MavenPublication>("maven") {
                afterEvaluate {
                    from(components["kotlin"])
                }
            }
        }
    }
}

plugins {
    kotlin("jvm") version "1.4.10" apply false
    kotlin("plugin.serialization") version "1.4.10" apply false
    `maven-publish`
}

allprojects {
    group = "com.johnturkson.aws-tools"
    version = "0.0.4"
    
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
                    username = System.getenv("USERNAME")
                    password = System.getenv("TOKEN")
                }
            }
        }
        
        publications {
            register<MavenPublication>("github") {
                afterEvaluate {
                    from(components["kotlin"])
                }
            }
        }
    }
}

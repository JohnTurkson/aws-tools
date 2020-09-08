plugins {
    `maven-publish`
}

allprojects {
    group = "com.johnturkson.aws-tools"
    version = "0.0.1"
    
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
            register<MavenPublication>("gpr") {
                afterEvaluate {
                    from(components["kotlin"])
                }
            }
        }
    }
}

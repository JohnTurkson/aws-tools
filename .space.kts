job("Publish artifacts") {
    container("openjdk:11") {
        env["SPACE_PUBLISHING_USERNAME"] = Secrets("space-publishing-username")
        env["SPACE_PUBLISHING_TOKEN"] = Secrets("space-publishing-token")
        
        kotlinScript { api ->
            if (api.gitBranch() == "refs/heads/main") {
                api.gradlew("build")
                api.gradlew("publish")
            }
        }
    }
}

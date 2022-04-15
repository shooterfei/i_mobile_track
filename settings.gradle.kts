pluginManagement {
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        google()
        mavenCentral()
    }
}
rootProject.name = "mobile_track"
include(":app")

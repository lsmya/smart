pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { setUrl("https://jitpack.io") }
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { setUrl("https://jitpack.io") }
        google()
        mavenCentral()
    }
}
rootProject.name = "智能工具"
include(":app")
include(":smart")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LoLDiary"
include(":app")
include(":feature:onboarding")
include(":core:data")
include(":core:domain")
include(":core:designsystem")
include(":feature:home")
include(":feature:blacklist")
include(":feature:diary")
include(":feature:main")
include(":core:idlingtest")
include(":core:test")

rootProject.name = "cookedapp"

includeBuild("build-logic")
include(":app")
include(":common")
include(":storage")

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    // Use version catalog
    versionCatalogs {
        create("libs") {
            from(files("./libs.versions.toml"))
        }
    }

    // Configure repositories
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

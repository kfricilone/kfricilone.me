pluginManagement {
    includeBuild("build-logic")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "kfricilone"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    "frontend",
    "backend"
)

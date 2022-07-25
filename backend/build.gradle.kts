plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlinx.kover")
    id("org.jmailen.kotlinter")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.dokka")
    id("com.google.cloud.tools.jib")
    application
}

dependencies {
    implementation(kotlin("stdlib-jdk8", libs.versions.kotlin.get()))
    implementation(libs.bundles.ktor)
    implementation(libs.kard)
    implementation(projects.frontend)
}

kotlin {
    explicitApi()
}

application {
    mainClass.set("me.kfricilone.ServerKt")
}

jib {
    to {
        image = rootProject.name
        tags = setOf("${rootProject.version}")
    }
    container {
        creationTime = "USE_CURRENT_TIMESTAMP"
        ports = listOf("8080")
    }
}

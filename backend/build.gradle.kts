plugins {
    id("application-conventions")
}

dependencies {
    implementation(libs.bundles.ktor)
    implementation(libs.kard)
    implementation(projects.frontend)
}

application {
    mainClass.set("me.kfricilone.ServerKt")
}

jib {
    container {
        ports = listOf("8080")
    }
}

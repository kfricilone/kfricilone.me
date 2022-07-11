import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import java.nio.charset.StandardCharsets
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    alias(libs.plugins.dependencies)

    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.jib) apply false
    alias(libs.plugins.webjar) apply false
}

group = Props.group
version = Props.version
description = "Personal website"

tasks.withType<Wrapper> {
    gradleVersion = libs.versions.gradle.get()
}

val rejectVersionRegex = Regex("(?i)[._-](?:alpha|beta|rc|cr|m|dev)")
tasks.withType<DependencyUpdatesTask> {
    gradleReleaseChannel = "current"
    revision = "release"

    rejectVersionIf {
        candidate.version.contains(rejectVersionRegex)
    }
}

repositories {
    mavenCentral()
}

subprojects {
    group = Props.group
    version = Props.version

    repositories {
        mavenLocal()
        mavenCentral()
    }

    plugins.withType<BasePlugin> {
        configure<BasePluginExtension> {
            archivesName.set("${rootProject.name}-$name")
        }
    }

    plugins.withType<ApplicationPlugin> {
        tasks.named<JavaExec>("run") {
            standardInput = System.`in`
            workingDir = rootDir
        }

        dependencies {
            val runtimeOnly by configurations
            runtimeOnly(libs.logback.classic)
        }
    }

    plugins.withType<JavaPlugin> {
        configure<JavaPluginExtension> {
            withSourcesJar()

            sourceCompatibility = Props.jvmVersion
            targetCompatibility = Props.jvmVersion
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = StandardCharsets.UTF_8.name()
        options.release.set(Props.jvmVersion.toString().toInt())
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = Props.jvmVersion.toString()
            freeCompilerArgs = Props.jvmArgs
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        jvmArgs = listOf("-ea")
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = Props.jvmVersion.toString()
    }

    tasks.withType<DetektCreateBaselineTask>().configureEach {
        jvmTarget = Props.jvmVersion.toString()
    }

    plugins.withType<MavenPublishPlugin> {
        apply(plugin = "org.gradle.signing")

        configure<PublishingExtension> {
            publications.withType<MavenPublication> {
                pom {
                    url.set("https://github.com/kfricilone/pasty")
                    inceptionYear.set("2021")

                    licenses {
                        license {
                            name.set("ISC License")
                            url.set("https://opensource.org/licenses/isc-license.txt")
                        }
                    }

                    developers {
                        developer {
                            name.set("Kyle Fricilone")
                            url.set("https://github.com/kfricilone")
                        }
                    }

                    scm {
                        connection.set("scm:git:https://github.com/kfricilone/pasty")
                        developerConnection.set("scm:git:git@github.com:kfricilone/pasty.git")
                        url.set("https://github.com/kfricilone/pasty")
                    }

                    issueManagement {
                        system.set("GitHub")
                        url.set("https://github.com/kfricilone/pasty/issues")
                    }

                    ciManagement {
                        system.set("GitHub")
                        url.set("https://github.com/kfricilone/pasty/actions?query=workflow%3Aci")
                    }
                }
            }

            configure<SigningExtension> {
                sign(publications)
            }
        }
    }
}

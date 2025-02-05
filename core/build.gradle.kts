plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("kotlinx-serialization")
    id("maven-publish")
}

group = "co.nimblehq.jsonapi"
version = "0.1.0"

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "co.nimblehq.jsonapi"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

publishing {
    repositories {
        maven {
            setUrl("https://maven.pkg.github.com/nimblehq/jsonapi-kotlin")
            credentials {
                username = (System.getenv("GITHUB_USER") ?: project.properties["GITHUB_USER"])?.toString()
                password = (System.getenv("GITHUB_TOKEN") ?: project.properties["GITHUB_TOKEN"])?.toString()
            }
        }
    }
}

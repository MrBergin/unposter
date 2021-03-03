plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group = "mr.bergin"
version = "1.0"

repositories {
    google()
}

dependencies {
    implementation(project(":common-ui"))
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "mr.bergin.android"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
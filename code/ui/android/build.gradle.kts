plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

group = "mr.bergin"
version = "1.0"

dependencies {
    implementation(project(":common-ui"))
    implementation("androidx.activity:activity-compose:1.3.0-alpha02")
    implementation("androidx.appcompat:appcompat:1.3.0-beta01")
    api("androidx.core:core-ktx:1.3.1")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

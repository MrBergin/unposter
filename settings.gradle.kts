rootProject.name = "unposter"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
        maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}

fun includeCode(projectName: String) {
    include(":$projectName")
    project(":$projectName").projectDir = File("code${File.separator}$projectName")
}

includeCode("model")
includeCode("cli")
includeCode("desktop-ui")
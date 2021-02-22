rootProject.name = "unposter"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
        maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
    }
}

fun includeCode(projectName: String) {
    include(":$projectName")
    project(":$projectName").projectDir = File("code${File.separator}$projectName")
}

includeCode("model")
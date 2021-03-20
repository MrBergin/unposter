import java.nio.file.Path

rootProject.name = "unposter"

pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:4.0.1")
            }
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
            }
            if (requested.id.id.startsWith("org.jetbrains.compose")) {
                useModule("org.jetbrains.compose:compose-gradle-plugin:0.3.2")
            }
        }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        jcenter()
        google()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}

fun include(projectName: String, path: Path) {
    include(":$projectName")
    project(":$projectName").projectDir = path.toFile()
}

include("model", Path.of("code", "model"))
include("cli", Path.of("code", "cli"))
include("desktop-ui", Path.of("code", "ui", "desktop"))
include("android-ui", Path.of("code", "ui", "android"))
include("common-ui", Path.of("code", "ui", "common"))
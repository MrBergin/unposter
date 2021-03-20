import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "mr.bergin"
version = "1.0"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":model"))
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-assertions-core:4.4.3")
                implementation("io.kotest:kotest-runner-junit5:4.4.3")
                implementation(kotlin("test-junit"))
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

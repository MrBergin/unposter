plugins {
    kotlin("multiplatform")
    kotlin("kapt")
}

group = "mr.bergin"
version = "0.0.1"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("dev.forkhandles:result4k:LOCAL")
            }
        }
        val jvmMain by getting {
            dependencies {
                api("io.arrow-kt:arrow-core:0.11.0")
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-assertions-core:4.4.1")
                implementation("io.kotest:kotest-assertions-arrow-jvm:4.4.1")
                implementation("io.kotest:kotest-runner-junit5:4.4.1")
                implementation(kotlin("test-junit"))
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
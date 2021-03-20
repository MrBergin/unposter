plugins {
    kotlin("multiplatform")
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
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("mr.bergin:result4kotest:LOCAL")
                implementation("io.kotest:kotest-assertions-core:4.4.3")
                implementation("io.kotest:kotest-framework-api:4.4.3")

            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5-jvm:4.4.3")
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
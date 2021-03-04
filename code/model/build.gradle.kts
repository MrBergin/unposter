import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = "mr.bergin"
version = "0.0.1"

dependencies {
    api("io.arrow-kt:arrow-core:0.11.0")
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")

    testImplementation("io.kotest:kotest-assertions-core:4.4.1")
    testImplementation("io.kotest:kotest-assertions-arrow-jvm:4.4.1")
    testImplementation("io.kotest:kotest-runner-junit5:4.4.1")
    testImplementation(kotlin("test-junit"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
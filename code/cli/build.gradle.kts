import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = "mr.bergin"
version = "0.0.1"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":model"))

    testImplementation("io.kotest:kotest-assertions-core:4.4.1")
    testImplementation("io.kotest:kotest-runner-junit5:4.4.1")
    testImplementation(kotlin("test-junit"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.5"
        apiVersion = "1.5"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
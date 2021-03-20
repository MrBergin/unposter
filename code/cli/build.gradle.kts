plugins {
    kotlin("multiplatform")
}

group = "mr.bergin"
version = "0.0.1"

dependencies {

}

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


            }
        }
        val jvmTest by getting {
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

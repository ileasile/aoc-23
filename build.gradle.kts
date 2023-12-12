plugins {
    kotlin("jvm") version "1.9.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlin-jupyter-api:0.12.0-96")
    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.5.0")

    implementation("org.jetbrains.kotlinx:dataframe:0.13.0-dev-2666")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

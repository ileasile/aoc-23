plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("jupyter.api") version "0.12.0-96"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:dataframe:0.12.0")
}

tasks.processJupyterApiResources {
    libraryProducers = listOf("AocIntegration")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

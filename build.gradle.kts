plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21" // Плагин сериализации
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0") // Библиотека для работы с JSON
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
/*
 * build.gradle.kts
 * Ditto Android buildSrc root Gradle project
 */

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    gradleApi()

    implementation(libs.kotlinpoet)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}

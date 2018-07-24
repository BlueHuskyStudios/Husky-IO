import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin2js") version "1.2.51"
    kotlin("jvm") version "1.2.51"
}

group = "org.bh.tools.io"
version = "0.0.1-lambda.1"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("stdlib-js"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
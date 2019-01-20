import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.11"
    id("com.github.johnrengelman.shadow") version "4.0.3"
}

group = "com.forresthopkinsa"
version = "0.0.2"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("net.md-5", "bungeecord-api", "1.7-SNAPSHOT")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val build by tasks.getting
val shadowJar by tasks.getting

build.dependsOn(shadowJar)
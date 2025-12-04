plugins {
    kotlin("jvm") version "1.9.23"
    application
    id("com.diffplug.spotless") version "6.25.0"
}

group = "com.cpu.sim"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}

kotlin {
    jvmToolchain(17) // usage of Java 17
}
spotless {
    kotlin {
        // Use the default ktlint configuration
        ktlint()
        // Optional: formatting options
        // ktlint("0.50.0") // to specify a version
    }
}
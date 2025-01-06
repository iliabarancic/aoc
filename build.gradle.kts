plugins {
    kotlin("jvm") version "2.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.0.1")

}
tasks.test {
    useJUnitPlatform()
}
tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}

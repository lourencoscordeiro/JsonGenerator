plugins {
    kotlin("jvm")
}

group = "pt.iscte.pa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
}

tasks { }
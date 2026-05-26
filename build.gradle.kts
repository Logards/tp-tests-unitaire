plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.14"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.kotest") version "6.1.11"
    id("jacoco")
    id("info.solidsoft.pitest") version "1.9.11"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "tp-tests-unitaires"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("io.mockk:mockk:1.14.9")
    testImplementation("io.kotest:kotest-assertions-core:6.1.11")
    testImplementation("io.kotest:kotest-property:6.1.11")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.kotest:kotest-runner-junit5:6.1.11")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
pitest {
    testPlugin.set("junit5")
    junit5PluginVersion.set("1.2.2")
    targetClasses.set(setOf("com.example.tptestsunitaires.*"))
    pitestVersion.set("1.19.1")
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.withType<Test> {
    useJUnitPlatform()
    tasks.test {
        finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    }}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }
    }
}

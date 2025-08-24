plugins {
    id("java-library")
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
tasks.test {
    useJUnitPlatform()
}
dependencies {
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    // Dagger
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    // Junit 5
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    // SLF4J
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)
    testImplementation(libs.slf4j.simple)
    // Coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    // MockK
    testImplementation(libs.mockk)
}

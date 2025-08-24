plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.criticalgnome.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
        }
    }
}

dependencies {
    // Modules
    implementation(project(":domain"))
    // Core
    implementation(libs.androidx.core.ktx)
    // Dagger
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    // Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Junit 5
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    // Coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    // MockK
    testImplementation(libs.mockk)
    // Room
    testImplementation(libs.room.testing)
    // Core Android
    androidTestImplementation(libs.runner)
    // Junit 5 Android
    androidTestImplementation(libs.junit.jupiter.api)
    androidTestImplementation(libs.android.test.core)
    androidTestRuntimeOnly(libs.android.test.runner)
    // MockK Android
    androidTestImplementation(libs.mockk.android)
}

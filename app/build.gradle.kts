plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.junit5)
}

android {
    namespace = "com.criticalgnome.cookbook"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.criticalgnome.cookbook"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Modules
    implementation(project(":data"))
    implementation(project(":domain"))
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.fragment.ktx)
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Dagger
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    // Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    // Junit 5
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)
    // Coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    // MockK
    testImplementation(libs.mockk)
    // Core Android
    androidTestImplementation(libs.runner)
    // Junit 5 Android
    androidTestImplementation(libs.junit.jupiter.api)
    androidTestImplementation(libs.android.test.core)
    androidTestRuntimeOnly(libs.android.test.runner)
    // MockK Android
    androidTestImplementation(libs.mockk.android)
}

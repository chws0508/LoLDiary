import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("convention.android.base")
    id("convention.android.hilt")
    id("convention.coroutine")
    id("kotlinx-serialization")
}

android {
    defaultConfig {
        buildConfigField(
            "String",
            "X_RIOT_TOKEN",
            gradleLocalProperties(rootDir).getProperty("X_RIOT_TOKEN"),
        )
    }
    namespace = "com.woosuk.data"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.skydoves.sandwich)

    // Network
    implementation(libs.retrofit.core)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)

    // Database
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.androidx.datastore)
}

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("convention.application")
}

android {
    namespace = "com.woosuk.loldiary"

    defaultConfig {
        applicationId = "com.woosuk.loldiary"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.woosuk.loldiary.CustomTestRunner"
        buildConfigField(
            "String",
            "X_RIOT_TOKEN",
            gradleLocalProperties(rootDir).getProperty("X_RIOT_TOKEN"),
        )
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:idlingtest"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:main"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:home"))
    implementation(project(":core:test"))

    // landScape
    implementation(libs.skydoves.landscapist.glide)
    implementation(libs.skydoves.landscapist.coil)
    implementation(libs.skydoves.landscapist.placeholder)

    // Splash API
    implementation(libs.androidx.core.splashscreen)
}

kapt {
    correctErrorTypes = true
}

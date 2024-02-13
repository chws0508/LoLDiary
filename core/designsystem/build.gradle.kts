plugins {
    id("com.android.library")
    id("convention.android.compose")
}

android {
    namespace = "com.woosuk.designsystem"
}

dependencies {
    // Splash API
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.compose.shimmer)
}

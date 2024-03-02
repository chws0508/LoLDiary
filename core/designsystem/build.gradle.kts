plugins {
    id("com.android.library")
    id("convention.android.compose")
}

android {
    namespace = "com.woosuk.designsystem"
}

dependencies {
    implementation(libs.compose.shimmer)
}

plugins {
    id("com.android.library")
    id("convention.android.base")
    id("convention.android.hilt")
}

android {
    namespace = "com.woosuk.idlingtest"
}

dependencies {
    implementation(libs.androidx.test.espresso.idling)
    androidTestImplementation(libs.androidx.test.espresso.idling)
}

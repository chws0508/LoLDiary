plugins {
   id("convention.feature")
}

android {
    namespace = "com.woosuk.home"
}

dependencies {
    // landScape
    implementation(libs.skydoves.landscapist.glide)
    implementation(libs.skydoves.landscapist.coil)
    implementation(libs.skydoves.landscapist.placeholder)
}

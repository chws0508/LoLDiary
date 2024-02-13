plugins {
    id("convention.feature")
}

android {
    namespace = "com.woosuk.main"
}

dependencies {
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:home"))
    implementation(project(":feature:blacklist"))
    implementation(project(":feature:diary"))
}

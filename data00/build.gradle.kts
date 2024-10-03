plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.javax.inject)

    // Firebase
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.auth)
//    implementation(libs.firebase.firestore)
//    implementation(libs.firebase.storage)
}
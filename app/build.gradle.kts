plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services) // for firebase init
}

android {
    namespace = "com.lumen1024.groupeventer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lumen1024.groupeventer"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0-alpha01"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions += "bundles"
    productFlavors {
        create("full") {
            dimension = "bundles"
            isDefault = true
        }
        create("dev") {
            dimension = "bundles"
            versionNameSuffix = "-dev"
            resourceConfigurations.clear()
            resourceConfigurations += listOf("en", "xxhdpi")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // modules
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":ui"))

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Splash
    implementation(libs.androidx.core.splashscreen)
}
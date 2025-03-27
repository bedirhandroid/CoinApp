import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.bedirhandroid.coinapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bedirhandroid.coinapp"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Navigation Component
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Hilt (KSP kullanarak)
    implementation(libs.hilt.android)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    ksp(libs.hilt.ksp)

    // Retrofit ve Gson
    implementation(libs.retrofit)
    implementation(libs.gson.converter)

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)

    implementation(project(":core"))
    implementation(project(":network"))
    implementation(project(":features"))
}

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    id("cooked-module")
}

android {
    namespace = "ru.cookedapp.common"

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(name = "proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
        }
    }
}

dependencies {

    // Kotlin
    implementation(libs.coroutines)
    implementation(libs.coroutines.android)
    // Jetpack
    implementation(libs.jetpack.core)
    implementation(libs.material)
    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
}

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions.jvmTarget = "11"
}

dependencies {

    // Kotlin
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)
    // Jetpack
    implementation(Dependencies.jetpackCore)
    implementation(Dependencies.material)
    // Test
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.junitExtensions)
}

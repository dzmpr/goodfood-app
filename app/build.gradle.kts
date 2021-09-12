plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
    id("kotlinx-serialization")
    id("io.gitlab.arturbosch.detekt").version("1.18.0")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "ru.cookedapp.cooked"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            manifestPlaceholders["appIconRes"] = "@mipmap/ic_launcher"
            manifestPlaceholders["appIconRoundRes"] = "@mipmap/ic_launcher_round"
            manifestPlaceholders["appNameRes"] = "@string/app_name"
        }

        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            manifestPlaceholders["appIconRes"] = "@mipmap/ic_launcher"
            manifestPlaceholders["appIconRoundRes"] = "@mipmap/ic_launcher_round"
            manifestPlaceholders["appNameRes"] = "@string/app_name_debug"
        }
    }

    compileOptions {
        // Enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    detekt {
        buildUponDefaultConfig = true
        allRules = false

        reports {
            html {
                enabled = true
                destination = file("build/detekt/report.html")
            }
            xml.enabled = false
            txt.enabled = false
            sarif.enabled = false
        }
    }
}

dependencies {
    val daggerVersion = "2.38.1"
    val lifecycleVersion = "2.3.1"
    val navigationVersion = "2.3.5"
    val roomVersion = "2.3.0"
    val preferenceVersion = "1.1.1"
    val kotlinVersion: String by rootProject.extra

    // Common
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    // AndroidX fragment
    implementation("androidx.fragment:fragment-ktx:1.3.6")
    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    // Dagger 2
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    // Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
    // ExifInterface
    implementation("androidx.exifinterface:exifinterface:1.3.3")
    // Preferences library
    implementation("androidx.preference:preference-ktx:$preferenceVersion")
    // Flexbox
    implementation("com.google.android.flexbox:flexbox:3.0.0")
}

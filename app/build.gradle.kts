import io.gitlab.arturbosch.detekt.Detekt

plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
    id("kotlinx-serialization")
    id("io.gitlab.arturbosch.detekt").version("1.19.0")
}

android {
    compileSdk = 31
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
            with(manifestPlaceholders) {
                put("appIconRes", "@mipmap/ic_launcher")
                put("appIconRoundRes", "@mipmap/ic_launcher_round")
                put("appNameRes", "@string/app_name")
            }
        }

        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            with(manifestPlaceholders) {
                put("appIconRes", "@mipmap/ic_launcher")
                put("appIconRoundRes", "@mipmap/ic_launcher_round")
                put("appNameRes", "@string/app_name_debug")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        jvmTarget = "11"
    }

    detekt {
        buildUponDefaultConfig = true
        allRules = false
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true)
            html.outputLocation.set(file("build/detekt/report.html"))
            xml.required.set(false)
            txt.required.set(false)
            sarif.required.set(false)
        }
    }
}

dependencies {
    val daggerVersion = "2.40.5"
    val lifecycleVersion = "2.4.0"
    val navigationVersion = "2.3.5"
    val roomVersion = "2.4.0"
    val preferenceVersion = "1.1.1"
    val kotlinVersion: String by rootProject.extra

    // Common
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // AndroidX fragment
    implementation("androidx.fragment:fragment-ktx:1.4.0")
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
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    // ExifInterface
    implementation("androidx.exifinterface:exifinterface:1.3.3")
    // Preferences library
    implementation("androidx.preference:preference-ktx:$preferenceVersion")
    // Flexbox
    implementation("com.google.android.flexbox:flexbox:3.0.0")
}

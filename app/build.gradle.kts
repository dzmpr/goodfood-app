import io.gitlab.arturbosch.detekt.Detekt

plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
    id("io.gitlab.arturbosch.detekt").version("1.19.0")
}

android {
    compileSdk = Config.compileSdk
    buildToolsVersion = Config.buildToolsVersion

    defaultConfig {
        applicationId = Config.appId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
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

    implementation(project(Modules.common))
    implementation(project(Modules.storage))

    // Common
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    // Jetpack
    implementation(Dependencies.jetpackCore)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.fragment)
    implementation(Dependencies.viewModel)
    implementation(Dependencies.liveData)
    implementation(Dependencies.navigation)
    implementation(Dependencies.navigationKtx)
    implementation(Dependencies.exifInterface)
    implementation(Dependencies.preference)

    // Dependencies
    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerKapt)
    implementation(Dependencies.flexbox)

    // Tests
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.junitExtensions)
}

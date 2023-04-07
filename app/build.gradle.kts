import io.gitlab.arturbosch.detekt.Detekt

// https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.app)
    alias(libs.plugins.detekt)
}

android {
    compileSdk = Config.compileSdk

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

    implementation(projects.common)
    implementation(projects.storage)

    // Common
    implementation(libs.coroutines)
    implementation(libs.coroutines.android)

    // Jetpack
    implementation(libs.jetpack.core)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.fragment)
    implementation(libs.viewmodel)
    implementation(libs.livedata)
    implementation(libs.navigation)
    implementation(libs.navigation.ktx)
    implementation(libs.exifinterface)
    implementation(libs.preference)

    // Dependencies
    implementation(libs.dagger)
    kapt(libs.dagger.kapt)
    implementation(libs.flexbox)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
}

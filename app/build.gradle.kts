import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.app)
    alias(libs.plugins.detekt)
    id("cooked-module")
}

android {
    namespace = "ru.cookedapp.cooked"

    defaultConfig {
        applicationId = "ru.cookedapp.cooked"
        versionCode = 1
        versionName = "0.1.0"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildFeatures {
        viewBinding = true
        compose = true
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
                put("appIconRes", "@mipmap/ic_launcher_debug")
                put("appIconRoundRes", "@mipmap/ic_launcher_round_debug")
                put("appNameRes", "@string/app_name_debug")
            }
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
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

tasks.withType<KotlinCompile> {
    with(compilerOptions.freeCompilerArgs) {
        add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
    }
}

dependencies {

    implementation(projects.common)
    implementation(projects.storage)

    // Jetpack
    implementation(libs.jetpack.core)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.fragment)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtimecompose)
    implementation(libs.lifecycle.livedata)
    implementation(libs.navigation)
    implementation(libs.navigation.ktx)
    implementation(libs.exifinterface)
    implementation(libs.splashscreen)

    // Dependencies
    implementation(libs.coroutines)
    implementation(libs.coroutines.android)
    implementation(libs.dagger)
    kapt(libs.dagger.kapt)
    implementation(libs.bundles.compose)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
}

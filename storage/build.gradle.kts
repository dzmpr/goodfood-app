// https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.ksp)
    id("cooked-module")
}

val roomSchemaDirectory = "$projectDir/schemas"

android {
    namespace = "ru.cookedapp.storage"

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

    sourceSets.configureEach {
        kotlin.srcDir("$buildDir/generated/ksp/$name/kotlin/")
    }

    sourceSets.getByName("androidTest").assets.srcDir(roomSchemaDirectory)
}

ksp {
    arg("room.schemaLocation", roomSchemaDirectory)
}

dependencies {

    implementation(projects.common)

    // Kotlin
    implementation(libs.coroutines)

    // Room
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.processor)

    // Common
    implementation(libs.dagger)
    kapt(libs.dagger.kapt)
    implementation(libs.serialization)
    implementation(libs.jetpack.core)

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.junit.ext)
    testImplementation(libs.junit.kotlin)

    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.junit.kotlin)
}

plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("kotlinx-serialization")
}

android {
    val roomSchemaDirectory = "$projectDir/schemas"

    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions.annotationProcessorOptions {
            arguments["room.schemaLocation"] = roomSchemaDirectory
        }
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

    sourceSets.getByName("androidTest").assets.srcDir(roomSchemaDirectory)
}

dependencies {

    implementation(project(Modules.common))

    // Kotlin
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.coroutinesCore)

    // Room
    implementation(Dependencies.room)
    implementation(Dependencies.roomKtx)
    kapt(Dependencies.roomKapt)

    // Common
    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerKapt)
    implementation(Dependencies.serialization)
    implementation(Dependencies.jetpackCore)

    // Test
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.junitExtensions)
    testImplementation(Dependencies.kotlinJunit)

    androidTestImplementation(Dependencies.junitExtensions)
    androidTestImplementation(Dependencies.kotlinJunit)
}

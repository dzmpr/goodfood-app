object Dependencies {

    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"

    // Jetpack
    const val jetpackCore = "androidx.core:core-ktx:${Versions.jetpackCore}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val exifInterface = "androidx.exifinterface:exifinterface:${Versions.exifInterface}"
    const val preference = "androidx.preference:preference-ktx:${Versions.preference}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomKapt = "androidx.room:room-compiler:${Versions.room}"
    const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"

    // Dependencies
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerKapt = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val flexbox = "com.google.android.flexbox:flexbox:${Versions.flexbox}"

    // Tests
    const val junit = "junit:junit:${Versions.junit}"
    const val kotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val junitExtensions = "androidx.test.ext:junit:${Versions.junitExtensions}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

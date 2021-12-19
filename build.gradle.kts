// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra["kotlinVersion"] = "1.6.10"

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        val kotlinVersion: String by rootProject.extra
        classpath("com.android.tools.build:gradle:7.0.4")

        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

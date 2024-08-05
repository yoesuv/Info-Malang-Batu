plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.perf.plugin) apply false
    alias(libs.plugins.firebase.crashlytics.plugin) apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val navVersion = project.properties["navigationVersion"].toString()
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}
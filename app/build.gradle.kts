import java.util.Properties

val apiKeyPropertiesFile = project.rootProject.file("apiKey.properties")
val apiKeyProperties = Properties()
apiKeyProperties.load(apiKeyPropertiesFile.inputStream())

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.perf.plugin)
    alias(libs.plugins.firebase.crashlytics.plugin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.safeArgs)
}

android {
    signingConfigs {
        create("config") {
            keyAlias = project.properties["KEY_ALIAS"].toString()
            keyPassword = project.properties["KEY_PASSWORD"].toString()
            storeFile = file("../-yusuf.keystore")
            storePassword = project.properties["STORE_PASSWORD"].toString()
        }
    }

    namespace = "com.yoesuv.infomalangbatu"
    compileSdk = 34

    defaultConfig {
        val keyMaps = apiKeyProperties["MAPS_API_KEY"].toString()
        val keyDirections = apiKeyProperties["DIRECTION_API_KEY"].toString()

        applicationId = "com.yoesuv.infomalangbatu"
        minSdk = 24
        targetSdk = 34
        versionCode = 17
        versionName = "2.3.5"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        setProperty("archivesBaseName", "$applicationId-v$versionCode($versionName)")
        buildConfigField("String", "BASE_URL", "\"https://info-malang-batu.firebaseapp.com/\"")
        resValue("string", "MAPS_API_KEY", keyMaps)
        resValue("string", "DIRECTION_API_KEY", keyDirections)
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("config")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("config")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    sourceSets {
        getByName("main") {
            res.srcDirs("src/main/res")
            res.srcDirs("src/main/res-gallery")
            res.srcDirs("src/main/res-listplace")
            res.srcDirs("src/main/res-maps")
            res.srcDirs("src/main/res-other")
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    flavorDimensions.add("default")
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.core.ktx)
    implementation(libs.appCompat)
    implementation(libs.material)
    implementation(libs.playServiceMaps)
    implementation(libs.playServiceLocation)

    implementation(libs.firebaseAnalytics)
    implementation(libs.firebaseCrashlytics)
    implementation(libs.firebasePerf)

    implementation(libs.lifecycleViewModel)
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.roomRuntime)
    ksp(libs.roomCompiler)
    implementation(libs.roomKtx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.httpLogging)

    implementation(libs.ssp.android)
    implementation(libs.sdp.android)
    implementation(libs.glide)
    ksp(libs.glideCompiler)
    implementation(libs.googleDirection)
    implementation(libs.multidex)
}
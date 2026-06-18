import com.google.firebase.perf.plugin.FirebasePerfExtension
import java.util.Properties

val apiKeyPropertiesFile = project.rootProject.file("apiKey.properties")
val apiKeyProperties = Properties()
apiKeyProperties.load(apiKeyPropertiesFile.inputStream())

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.safeArgs)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.perf.plugin)
    alias(libs.plugins.firebase.crashlytics.plugin)
    id("jacoco")
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
    compileSdk = 36

    defaultConfig {
        val keyMaps = apiKeyProperties["MAPS_API_KEY"].toString()
        val keyDirections = apiKeyProperties["DIRECTION_API_KEY"].toString()

        applicationId = "com.yoesuv.infomalangbatu"
        minSdk = 24
        targetSdk = 36
        versionCode = 19
        versionName = "2.3.7"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "BASE_URL", "\"https://info-malang-batu.firebaseapp.com/\"")
        resValue("string", "MAPS_API_KEY", keyMaps)
        resValue("string", "DIRECTION_API_KEY", keyDirections)
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("config")
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
            configure<FirebasePerfExtension> {
                setInstrumentationEnabled(false)
            }
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("config")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        getByName("main") {
            res {
                directories += listOf(
                    "src/main/res",
                    "src/main/res-gallery",
                    "src/main/res-listplace",
                    "src/main/res-maps",
                    "src/main/res-other"
                )
            }
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
        resValues = true
    }
    flavorDimensions.add("default")
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

// JaCoCo configuration for code coverage
tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
        isEnabled = true
        output = JacocoTaskExtension.Output.FILE
    }
}

tasks.register("jacocoTestReport", JacocoReport::class) {
    dependsOn("testDebugUnitTest", "connectedDebugAndroidTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/jacocoTestReport/jacocoTestReport.xml"))
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/jacocoTestReport/html"))
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
    )

    val debugTree = fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
        exclude(fileFilter)
    }

    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(
        fileTree(layout.buildDirectory) {
            include(
                "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec",
                "outputs/code_coverage/debugAndroidTest/connected/**/coverage.ec"
            )
        }
    )
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.fragmentTesting)
    debugImplementation(libs.arch.core.testing)
    debugImplementation(libs.mockito.core)
    debugImplementation(libs.mockito.kotlin)
    debugImplementation(libs.coroutines.test)
    debugImplementation(libs.coroutines.core)
    debugImplementation(libs.arch.core.testing)

    implementation(libs.androidx.core.ktx)
    implementation(libs.appCompat)
    implementation(libs.material)
    implementation(libs.playServiceMaps)
    implementation(libs.playServiceLocation)

    implementation(platform(libs.firebaseBom))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-perf")

    implementation(libs.lifecycleViewModel)
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.roomRuntime)
    kapt(libs.roomCompiler)
    implementation(libs.roomKtx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.httpLogging)

    implementation(libs.ssp.android)
    implementation(libs.sdp.android)
    implementation(libs.glide)
    kapt(libs.glideCompiler)
    implementation(libs.googleDirection)
}
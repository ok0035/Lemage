import Libs.PAGING
import Libs.androidTestImplementations
import Libs.implementations
import Libs.kaptAndroidTests
import Libs.kapts
import Libs.testImplementations

plugins {
    kotlin("kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("androidx.room")
}

android {
    namespace = "com.zerosword.domain"
    compileSdk = AppConfig.COMPILE_SDK_VER

    defaultConfig {
        minSdk = AppConfig.MIN_SDK_VER

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = AppConfig.javaVersion
        targetCompatibility = AppConfig.javaVersion
    }
    
    kotlinOptions {
        jvmTarget = AppConfig.JVM_TARGET_VER
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementations(
        listOf(
            Libs.CORE_KTX,
            Libs.HILT,
            PAGING,
            Libs.ROOM_RUNTIME,
            Libs.ROOM_KTX,
            Libs.ROOM_PAGING
        )
    )

    kapts(
        listOf(
            Libs.HILT_COMPILER,
            Libs.ROOM_COMPILER
        )
    )

    testImplementations(
        listOf(
            Libs.JUNIT,
            Libs.HILT_ANDROID_TEST,
            Libs.PAGING_TEST
        )
    )

    androidTestImplementations(
        listOf(
            Libs.ANDROIDX_TEST_JUNIT,
            Libs.ANDROIDX_ESPRESSO_CORE,
            Libs.HILT_ANDROID_TEST
        )
    )

    kaptAndroidTests(
        listOf(
            Libs.HILT_COMPILER
        )
    )
}
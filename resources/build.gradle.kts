import Libs.androidTestImplementations
import Libs.debugImplementations
import Libs.implementations
import Libs.kapts
import Libs.testImplementations

plugins {
    kotlin("kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.zerosword.resources"
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
    compileOptions {
        sourceCompatibility = AppConfig.javaVersion
        targetCompatibility = AppConfig.javaVersion
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.KOTLIN_COMPILER_EXT_VER
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
            platform(Libs.COMPOSE_BOM),
            Libs.CORE_KTX,
            Libs.RETROFIT,
            Libs.ACTIVITY_COMPOSE,
            Libs.COMPOSE_UI,
            Libs.COMPOSE_UI_GRAPHICS,
            Libs.COMPOSE_UI_TOOLING_PREVIEW,
            Libs.MATERIAL3,
        )
    )

    kapts(
        listOf(
            Libs.HILT_COMPILER
        )
    )

    testImplementations(
        listOf(
            Libs.JUNIT,
        )
    )

    androidTestImplementations(
        listOf(
            platform(Libs.COMPOSE_BOM),
            Libs.ANDROIDX_TEST_JUNIT,
            Libs.ANDROIDX_ESPRESSO_CORE,
            Libs.COMPOSE_UI_TEST_JUNIT,
        )
    )

    debugImplementations(
        listOf(
            Libs.COMPOSE_UI_TOOLING,
            Libs.COMPOSE_UI_TEST_MANIFEST
        )
    )

}
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
}

android {
    namespace = "com.zerosword.data"
    compileSdk = AppConfig.COMPILE_SDK_VER

    defaultConfig {
        minSdk = AppConfig.MIN_SDK_VER

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField(
                type = "String",
                name = "baseUrl",
                value = "\"https://httpbin.org/\""
            )
        }
        release {
            buildConfigField(
                type = "String",
                name = "baseUrl",
                value = "\"https://httpbin.org/\""
            )

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = AppConfig.javaVersion
        targetCompatibility = AppConfig.javaVersion
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTargetVer
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementations(
        listOf(
            project(":domain"),
            platform(Libs.OK_HTTP_CLIENT_BOM),
            Libs.GSON,
            Libs.CORE_KTX,
            Libs.RETROFIT,
            Libs.RETROFIT_GSON_CONVERTER,
            Libs.OK_HTTP_CLIENT,
            Libs.OK_HTTP_INTERCEPTOR,
            Libs.SANDWICH,
            Libs.SANDWICH_FOR_RETROFIT,
            Libs.HILT
        )
    )
    testImplementations(
        listOf(
            Libs.JUNIT,
            Libs.OK_HTTP_MOCK_WEB_SERVER,
            Libs.HILT_ANDROID_TEST
        )
    )

    kapts(
        listOf(
            Libs.HILT_COMPILER
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

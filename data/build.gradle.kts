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
    id("kotlin-parcelize")
}

android {
    namespace = "com.zerosword.data"
    compileSdk = AppConfig.COMPILE_SDK_VER

    defaultConfig {
        minSdk = AppConfig.MIN_SDK_VER

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            type = "String",
            name = "kakaoApiKey",
            value = "\"47487addb7969f48f3aad637e0d0c440\""
        )

        buildConfigField(
            type = "String",
            name = "baseUrl",
            value = "\"https://dapi.kakao.com/\""
        )

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
            Libs.HILT,
            Libs.PAGING
        )
    )
    testImplementations(
        listOf(
            Libs.JUNIT,
            Libs.OK_HTTP_MOCK_WEB_SERVER,
            Libs.HILT_ANDROID_TEST,
            Libs.PAGING_TEST
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

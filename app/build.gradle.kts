import Libs.androidTestImplementations
import Libs.debugImplementations
import Libs.implementations
import Libs.kaptAndroidTests
import Libs.kaptTests
import Libs.kapts
import Libs.testImplementations

plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.zerosword.template"
    compileSdk = AppConfig.COMPILE_SDK_VER

    defaultConfig {
        applicationId = "com.zerosword.template"
        minSdk = AppConfig.MIN_SDK_VER
        targetSdk = AppConfig.TARGET_SDK_VER
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = AppConfig.jvmTargetVer
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtVer
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    implementations(
        listOf(
            project(":data"),
            project(":domain"),
            project(":feature-main"),
            project(":resources"),
            platform(Libs.COMPOSE_BOM),
            platform(Libs.OK_HTTP_CLIENT_BOM),
            Libs.CORE_KTX,
            Libs.LIFECYCLE_RUNTIME_KTX,
            Libs.LIFECYCLE_FOR_COMPOSE,
            Libs.LIFECYCLE_SERVICE,
            Libs.VIEWMODEL,
            Libs.VIEWMODEL_FOR_COMPOSE,
            Libs.VIEWMODEL_FOR_SAVED_STATE,
            Libs.RETROFIT,
            Libs.RETROFIT_GSON_CONVERTER,
            Libs.OK_HTTP_CLIENT,
            Libs.OK_HTTP_INTERCEPTOR,
            Libs.SANDWICH,
            Libs.SANDWICH_FOR_RETROFIT,
            Libs.ACTIVITY_COMPOSE,
            Libs.COMPOSE_UI,
            Libs.COMPOSE_UI_GRAPHICS,
            Libs.COMPOSE_UI_TOOLING_PREVIEW,
            Libs.MATERIAL3,
            Libs.GLIDE,
            Libs.GLIDE_FOR_COMPOSE,
            Libs.HILT
        )
    )

    kapts(
        listOf(
            Libs.HILT_COMPILER,
            Libs.LIFECYCLE_COMPILER,
            Libs.GLIDE
        )
    )
    kaptTests(listOf(Libs.HILT_COMPILER))

    testImplementations(
        listOf(
            Libs.JUNIT,
            Libs.OK_HTTP_MOCK_WEB_SERVER,
            Libs.HILT_ANDROID_TEST
        )
    )

    androidTestImplementations(
        listOf(
            platform(Libs.COMPOSE_BOM),
            Libs.ANDROIDX_TEST_JUNIT,
            Libs.ANDROIDX_ESPRESSO_CORE,
            Libs.COMPOSE_UI_TEST_JUNIT,
            Libs.HILT_ANDROID_TEST
        )
    )

    kaptAndroidTests(
        listOf(
            Libs.HILT_COMPILER
        )
    )

    debugImplementations(
        listOf(
            Libs.COMPOSE_UI_TOOLING,
            Libs.COMPOSE_UI_TEST_MANIFEST
        )
    )

}



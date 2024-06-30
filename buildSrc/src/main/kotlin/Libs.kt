import org.gradle.api.artifacts.dsl.DependencyHandler

object Libs {

    const val CORE_KTX = "androidx.core:core-ktx:1.12.0"

    //Retrofit
    private const val RETROFIT_VER = "2.10.0"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VER"
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:$RETROFIT_VER"

    //OkHttpClient
    private const val OK_HTTP_CLIENT_VER = "4.12.0"
    const val OK_HTTP_CLIENT_BOM = "com.squareup.okhttp3:okhttp-bom:$OK_HTTP_CLIENT_VER"
    const val OK_HTTP_CLIENT = "com.squareup.okhttp3:okhttp"
    const val OK_HTTP_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor"
    const val OK_HTTP_MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:$OK_HTTP_CLIENT_VER"

    //Gson
    const val GSON = "com.google.code.gson:gson:2.10.1"

    //Sandwich
    private const val SANDWICH_VER = "2.0.6"
    const val SANDWICH = "com.github.skydoves:sandwich:$SANDWICH_VER"
    const val SANDWICH_FOR_RETROFIT =
        "com.github.skydoves:sandwich-retrofit:$SANDWICH_VER" // For Retrofit

    //Hilt
    private const val HILT_VER = "2.51"
    const val HILT = "com.google.dagger:hilt-android:$HILT_VER"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:$HILT_VER"
    const val HILT_ANDROID_TEST = "com.google.dagger:hilt-android-testing:$HILT_VER"
    const val HILT_FOR_COMPOSE = "androidx.hilt:hilt-navigation-compose:1.0.0"

    //Compose
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:1.8.2"
    const val COMPOSE_BOM = "androidx.compose:compose-bom:2023.08.00"
    const val COMPOSE_UI = "androidx.compose.ui:ui"
    const val COMPOSE_UI_GRAPHICS = "androidx.compose.ui:ui-graphics"
    const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
    const val COMPOSE_UI_TEST_JUNIT = "androidx.compose.ui:ui-test-junit4"
    const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling"
    const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest"

    //Image
    const val GLIDE = "com.github.bumptech.glide:glide:4.14.2" // lib + kapt
    const val GLIDE_FOR_COMPOSE = "com.github.bumptech.glide:compose:1.0.0-beta01"
    const val COIL = "io.coil-kt:coil-compose:2.6.0"

    //Test
    const val MATERIAL3 = "androidx.compose.material3:material3"
    const val JUNIT = "junit:junit:4.13.2"
    const val ANDROIDX_TEST_JUNIT = "androidx.test.ext:junit:1.1.5"
    const val ANDROIDX_ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.5.1"

    //Lifecycle
    private const val LIFECYCLE_VER = "2.7.0"
    const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VER"
    const val VIEWMODEL_FOR_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:$LIFECYCLE_VER"
    const val LIFECYCLE_FOR_COMPOSE = "androidx.lifecycle:lifecycle-runtime-compose:$LIFECYCLE_VER"
    const val VIEWMODEL_FOR_SAVED_STATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$LIFECYCLE_VER"
    const val LIFECYCLE_COMPILER = "androidx.lifecycle:lifecycle-compiler:$LIFECYCLE_VER"
    const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VER"
    const val LIFECYCLE_SERVICE = "androidx.lifecycle:lifecycle-service:$LIFECYCLE_VER"

    fun DependencyHandler.kapts(list: List<String>) {
        list.forEach { dependency ->
            add("kapt", dependency)
        }
    }

    fun DependencyHandler.implementations(list: List<Any>) {
        list.forEach { dependency ->
            add("implementation", dependency)
        }
    }

    fun DependencyHandler.testImplementations(list: List<String>) {
        list.forEach { dependency ->
            add("testImplementation", dependency)
        }
    }

    fun DependencyHandler.androidTestImplementations(list: List<Any>) {
        list.forEach { dependency ->
            add("androidTestImplementation", dependency)
        }
    }

    fun DependencyHandler.debugImplementations(list: List<String>) {
        list.forEach { dependency ->
            add("debugImplementation", dependency)
        }
    }

    fun DependencyHandler.kaptAndroidTests(list: List<String>) {
        list.forEach { dependency ->
            add("androidTestAnnotationProcessor", dependency)
        }
    }

    fun DependencyHandler.kaptTests(list: List<String>) {
        list.forEach { dependency ->
            add("kaptTest", dependency)
        }
    }

}
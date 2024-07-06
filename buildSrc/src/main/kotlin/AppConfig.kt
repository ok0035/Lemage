import org.gradle.api.JavaVersion

object AppConfig {

    const val COMPILE_SDK_VER = 34
    const val MIN_SDK_VER = 26
    const val TARGET_SDK_VER = 34
    const val VERSION_CODE = 3
    const val VERSION_NAME = "1.0.2"

    const val JVM_TARGET_VER = "11"
    const val KOTLIN_COMPILER_EXT_VER = "1.5.11"

    val javaVersion = JavaVersion.VERSION_11

}
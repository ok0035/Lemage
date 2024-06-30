import org.gradle.api.JavaVersion

object AppConfig {

    const val COMPILE_SDK_VER = 34
    const val MIN_SDK_VER = 26
    const val TARGET_SDK_VER = 34
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val jvmTargetVer = "11"
    const val kotlinCompilerExtVer = "1.5.11"

    val javaVersion = JavaVersion.VERSION_11

}
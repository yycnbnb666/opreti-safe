import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}


// 复用预编译原生库开关（仅用于无 NDK 的 ARM64 本机构建）：
// 打开后 Gradle 不再执行 CMake，直接打包模块既有的 jniLibs/.so。
// 默认关闭，CI 仍然完整编译原生代码。
val usePrebuiltNativeLibs =
    providers.gradleProperty("operit.prebuiltNativeLibs").orNull == "true"

android {
    namespace = "com.ai.assistance.quickjs"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        if (!usePrebuiltNativeLibs) {
            externalNativeBuild {
                cmake {
                    cppFlags("-std=c++17")
                }
            }
        }

        ndk {
            abiFilters.addAll(listOf("arm64-v8a"))
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
        debug {
            isMinifyEnabled = false
        }
    }

    if (!usePrebuiltNativeLibs) {
        externalNativeBuild {
            cmake {
                path = file("src/main/cpp/CMakeLists.txt")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    implementation(libs.kotlinx.serialization)
}

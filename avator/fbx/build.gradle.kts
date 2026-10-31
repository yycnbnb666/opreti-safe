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
    namespace = "com.ai.assistance.fbx"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ndk {
            abiFilters.addAll(listOf("arm64-v8a"))
        }

        if (!usePrebuiltNativeLibs) {
            externalNativeBuild {
                cmake {
                    cppFlags += listOf("-std=c++17")
                    arguments += listOf(
                        "-DANDROID_STL=c++_static",
                        "-DANDROID_PLATFORM=android-26",
                        "-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON"
                    )
                }
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    if (!usePrebuiltNativeLibs) {
        externalNativeBuild {
            cmake {
                path = file("CMakeLists.txt")
                version = "3.22.1"
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

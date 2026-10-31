import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}


// 复用预编译原生库开关（仅用于无 NDK 的 ARM64 本机构建）：
// 打开后 Gradle 不再执行 CMake，直接打包模块既有的 jniLibs/.so。
// 默认关闭，CI 仍然完整编译原生代码。
val usePrebuiltNativeLibs =
    providers.gradleProperty("operit.prebuiltNativeLibs").orNull == "true"

android {
    namespace = "com.dragonbones"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        ndk {
            abiFilters.addAll(listOf("arm64-v8a"))
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        if (!usePrebuiltNativeLibs) {
            externalNativeBuild {
                cmake {
                    arguments += listOf("-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON")
                }
            }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")

    if (!usePrebuiltNativeLibs) {
        externalNativeBuild {
            cmake {
                path = file("CMakeLists.txt")
                version = "3.22.1"
            }
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    
    // Compose 依赖
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.activity.compose)
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    
    // 调试工具
    debugImplementation(libs.compose.ui.tooling)
} 

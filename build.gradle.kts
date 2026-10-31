buildscript {
    val objectboxVersion by extra("5.3.0")
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.objectbox:objectbox-gradle-plugin:$objectboxVersion")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

// 本机 SDK 仅提供 build-tools 36.0.0，而 AGP 默认要求 35.0.0。
// 统一把所有 Android 模块的 buildToolsVersion 指向已安装的 36.0.0。
allprojects {
    plugins.withId("com.android.application") {
        extensions.configure<com.android.build.api.dsl.ApplicationExtension>("android") {
            buildToolsVersion = "36.0.0"
        }
    }
    plugins.withId("com.android.library") {
        extensions.configure<com.android.build.api.dsl.LibraryExtension>("android") {
            buildToolsVersion = "36.0.0"
        }
    }
}

tasks.register("assembleDebugClone") {
    dependsOn(":app:assembleClone")
    description = "Build the clone (co-installable) debug APK with package name suffix .clone"
}

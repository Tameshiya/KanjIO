plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("KanjiDb") {
        packageName = "jp.rei.andou.kanjio"
    }
}

val ideaActive = System.getProperty("idea.active") == "true"

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.3"
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    sourceSets.all {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
}

kotlin {
    android()
    ios()

    val iosArm32 = iosArm32("iosArm32")
    val iosArm64 = iosArm64("iosArm64")
    val iosX64 = iosX64("iosX64")

    if (ideaActive) {
        iosX64("ios")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.3.0")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("com.squareup.sqldelight:android-driver:1.3.0")
            }
        }
        all {
            languageSettings.apply {
                languageVersion = "1.3" // possible values: '1.0', '1.1', '1.2', '1.3'
                apiVersion = "1.3" // possible values: '1.0', '1.1', '1.2', '1.3'
                useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes") // annotation FQ-name
                progressiveMode = true // false by default
            }
        }

        val iosMain = getByName("iosMain")

        iosMain.apply {
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:1.3.0")
                // implementation("com.squareup.sqldelight:ios-driver:1.2.1")
            }
        }

        val iosArm32Main by getting {}
        val iosArm64Main by getting {}
        val iosX64Main by getting {}

        configure(listOf(iosArm32Main, iosArm64Main, iosX64Main)) {
            dependsOn(iosMain)
        }
    }

    val frameworkName = "shared"

    configure(listOf(iosArm32, iosArm64, iosX64)) {
        compilations {
            val main by getting {
                //extraOpts("-Xobjc-generics")
            }
        }

        binaries.framework {
            //export("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.2")
            export("com.squareup.sqldelight:native-driver:1.3.0")
            baseName = frameworkName
        }
    }

    tasks.register<org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask>("debugFatFramework") {
        baseName = frameworkName
        group = "Universal framework"
        description = "Builds a universal (fat) debug framework"

        from(iosX64.binaries.getFramework("DEBUG"))
    }

    tasks.register<org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask>("releaseFatFramework") {
        baseName = frameworkName
        group = "Universal framework"
        description = "Builds a universal (release) debug framework"

        from(iosArm64.binaries.getFramework("RELEASE"), iosArm32.binaries.getFramework("RELEASE"))
    }

}
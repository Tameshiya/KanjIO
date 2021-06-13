import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

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

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
    }
    sourceSets.all {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }

    //workaround for https://youtrack.jetbrains.com/issue/KT-43944
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

kotlin {
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    android()
    ios()

    sourceSets.all {
        languageSettings.apply {
            languageVersion = "1.5"
            apiVersion = "1.5"
            useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes") // annotation FQ-name
            progressiveMode = true // false by default
        }
    }

    sourceSets["commonMain"].dependencies {
        implementation("com.squareup.sqldelight:coroutines-extensions:1.3.0")
    }

    sourceSets["androidMain"].dependencies {
        //implementation("com.squareup.sqldelight:android-driver:1.3.0")
    }

    sourceSets["iosMain"].dependencies {
        implementation("com.squareup.sqldelight:native-driver:1.3.0")
    }

}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)
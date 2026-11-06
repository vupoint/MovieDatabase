package io.viewpoint.moviedatabase.buildlogic.extensions

import com.android.build.api.dsl.CommonExtension
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension,
) {
    commonExtension.apply {
        compileSdk = compileSdkVersion

        defaultConfig.apply {
            minSdk = minSdkVersion
        }

        compileOptions.apply {
            // Up to Java 11 APIs are available through desugaring
            // https://developer.android.com/studio/write/java11-minimal-support-table
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }
    }

    extensions.configure<KotlinAndroidExtension> {
        configureKotlin(compilerOptions)
    }

    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("android-desugarJdkLibs").get())
    }

    extensions.configure<KoverProjectExtension> {
        currentProject {
            createVariant("merged") {
                add("debug", optional = false)
            }
        }
    }
}

/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        // Up to Java 11 APIs are available through desugaring
        // https://developer.android.com/studio/write/java11-minimal-support-table
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    extensions.configure<KotlinJvmProjectExtension> {
        configureKotlin(compilerOptions)
    }

    extensions.configure<KoverProjectExtension> {
        currentProject {
            createVariant("merged") {
                add("jvm", optional = false)
            }
        }
    }
}

/**
 * Configure base Kotlin options
 */
private fun Project.configureKotlin(options: KotlinJvmCompilerOptions) = options.apply {
    // Treat all Kotlin warnings as errors (disabled by default)
    // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
    val warningsAsErrors = providers.gradleProperty("warningsAsErrors").orNull
    jvmTarget = JvmTarget.JVM_17
    allWarningsAsErrors = warningsAsErrors.toBoolean()
    freeCompilerArgs.add(
        // Enable experimental coroutines APIs, including Flow
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
    )
}

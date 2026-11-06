import io.viewpoint.moviedatabase.buildlogic.extensions.Externals

plugins {
    alias(libs.plugins.moviedatabase.android.library)
    alias(libs.plugins.moviedatabase.jvm.external)
    alias(libs.plugins.ksp)
}

android {
    namespace = "io.viewpoint.moviedatabase.core.data"

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", "\"${Externals.apiKey}\"")
            buildConfigField("int", "DATABASE_VERSION", "1")
        }
        release {
            buildConfigField("String", "API_KEY", "\"${Externals.apiKey}\"")
            buildConfigField("int", "DATABASE_VERSION", "1")
        }
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(project(":core:common-coroutines"))
    implementation(project(":core:domain"))
    implementation(project(":core:api"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room)
    ksp(libs.androidx.room.compiler)

    implementation(libs.timber)

    implementation(libs.okhttp)

    // test
    testImplementation(project(":test:mock"))
    testImplementation(libs.test.junit)
    testImplementation(libs.test.strikt)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.mockk.agent.jvm)
    testImplementation(libs.moshi)
    testImplementation(libs.test.coroutine.test)

    androidTestImplementation(libs.androidTestJunit)
    androidTestImplementation(libs.androidTestRunner)

    debugImplementation(libs.flipper)
    debugImplementation(libs.soloader)
    debugImplementation(libs.flipper.network)
}

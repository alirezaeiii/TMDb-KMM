import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.skie)
    alias(libs.plugins.multiplatformResources)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            freeCompilerArgs += "-Xbinary=bundleId=org.example.tmdb"
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)

            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.plugin.logging)
            implementation(libs.ktor.plugin.content.negotiation)
            implementation(libs.kotlinx.serialization)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.napier)
            implementation(libs.moko.resources)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        all {
            languageSettings {
                optIn("kotlin.ExperimentalMultiplatform")
            }
        }
    }
}

android {
    namespace = "org.example.tmdb.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

buildkonfig {
    packageName = "org.example.tmdb"

    defaultConfigs {
        val apiKey: String = gradleLocalProperties(rootDir, providers).getProperty("TMDB_API_KEY")
        buildConfigField(FieldSpec.Type.STRING, "TMDB_API_KEY", apiKey)
    }
}

skie {
    features {
        enableSwiftUIObservingPreview = true
    }
}

multiplatformResources {
    resourcesPackage.set("org.example.tmdb")
}

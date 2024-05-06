import com.android.build.gradle.tasks.factory.AndroidUnitTest
import live.ditto.gradle.EnvGradleTask
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    metadata {
        compilations.configureEach {
            // Custom task which generates the Env object. Needs to be run before compileCommonMainKotlinMetadata
            compileTaskProvider.get().dependsOn("envTask")
        }
    }
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
            sourceSetTree.set(KotlinSourceSetTree.test)

            dependencies {
                implementation(libs.compose.ui.test.junit4.android)
                debugImplementation(libs.compose.ui.test.manifest)
            }
        }
    }

    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")

        pod("DittoObjC") {
            version = "4.7.1"
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.koin.core)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.runtime)
            implementation(compose.ui)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.accompanist.permissions)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.ditto)
            implementation(libs.koin.android)
        }
    }
}

android {
    namespace = "live.ditto.demo.kotlinmultipeer"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    android.buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "live.ditto.demo.kotlinmultipeer"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)

        testImplementation(libs.kotlin.test)
        testImplementation(libs.testing.junit)
        androidTestImplementation(libs.kotlin.test)
        androidTestImplementation(libs.testing.junit)
    }
}

tasks {
    // Dummy testClasses task to resolve error:
    // > Cannot locate tasks that match ':shared:testClasses' as task 'testClasses' not found in project ':shared'.
    val testClasses by creating

    // Android app environment variables
    val envFile = rootProject.file("env.properties")
    if (!envFile.exists()) {
        throw Exception(
            "Missing env.properties file. Please copy the env.properties.example template and fill in with your app details.",
        )
    }
    val env = Properties()
    env.load(FileInputStream(envFile))

    val envTask by registering(EnvGradleTask::class) {
        className = "Env"
        packageName = ""
        sourceDir = file("src/commonMain/kotlin")
        DEBUG = true
        VERSION = project.version as String
        DITTO_APP_ID = env["DITTO_APP_ID"] as String
        DITTO_OFFLINE_TOKEN = env["DITTO_OFFLINE_TOKEN"] as String
        DITTO_PLAYGROUND_TOKEN = env["DITTO_PLAYGROUND_TOKEN"] as String
    }

    // compileDebugKotlinAndroid
    withType<KotlinCompile> {
        // Ensure the [Env] object has been generated
        dependsOn(envTask)
    }
    // compileKotlinIosSimulatorArm64
    withType<KotlinNativeCompile> {
        dependsOn(envTask)
    }

    clean {
        // Remove generated Env.kt file
        delete += listOf("$rootDir/composeApp/src/commonMain/kotlin/Env.kt")
    }
}

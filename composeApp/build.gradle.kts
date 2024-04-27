import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

//    listOf(
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach { iosTarget ->
//        iosTarget.binaries.framework {
//            baseName = "ComposeApp"
//            isStatic = true
//        }
//    }

    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")

        pod("DittoObjC") {
            version = "4.7.1-rc.3"
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

        // Android app environment variables
        val envFile = rootProject.file("env.properties")
        if (!envFile.exists()) {
            throw Exception("Missing env.properties file. Please copy the env.properties.example template and fill in with your app details.")
        }
        val env = Properties()
        env.load(FileInputStream(envFile))
        // Explicit double-quotes are needed in the string value in order to be a valid string in
        // the generated BuildConfig.java file.
        buildConfigField("String", "DITTO_APP_ID", "\"" + env["DITTO_APP_ID"] + "\"")
        buildConfigField("String", "DITTO_PLAYGROUND_TOKEN", "\"" + env["DITTO_PLAYGROUND_TOKEN"] + "\"")
        buildConfigField("String", "DITTO_OFFLINE_TOKEN", "\"" + env["DITTO_OFFLINE_TOKEN"] + "\"")
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
    }
}

tasks {
    // Dummy testClasses task to resolve error:
    // > Cannot locate tasks that match ':shared:testClasses' as task 'testClasses' not found in project ':shared'.
    val testClasses by creating
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.detekt)
    alias(libs.plugins.junit)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

android {
    compileSdk = 34
    namespace = "org.sabaini.pokedex"

    defaultConfig {
        applicationId = "org.sabaini.pokedex"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro",
            )
            // TODO: for development purposes, remember to create a release signing config when releasing proper app
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    kotlin {
        jvmToolchain(17)
    }

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
        )
    }

    packaging {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.coil)
    implementation(libs.compose.material3)
    implementation(libs.material.icons)
    implementation(libs.hilt)
    implementation(libs.kotlin.coroutines)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.navigation)
    implementation(libs.navigation.hilt)
    implementation(libs.kotlin.serialization)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.room)
    implementation(libs.room.ktx)
    implementation(libs.timber)
    implementation(libs.speed.dial)
    implementation(libs.paging)
    implementation(libs.paging.runtime)
    implementation(libs.palette)
    implementation(libs.activity)
    testImplementation(libs.bundles.common.test)
    androidTestImplementation(libs.bundles.common.android.test)
    debugImplementation(libs.debug.compose.manifest)
    debugImplementation(libs.ui.tooling)

    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)
    kspAndroidTest(libs.test.android.hilt.compiler)

    coreLibraryDesugaring(libs.desugar)

    detektPlugins(libs.detekt.compose.rules)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

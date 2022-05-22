plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "dev.sebastiano.customthemeplayground"
    compileSdk = 32

    defaultConfig {
        applicationId = "dev.sebastiano.customthemeplayground"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val composeUiVersion: String by rootProject.extra

    implementation("androidx.core:core-ktx:1.8.0-rc01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-rc01")
    implementation("androidx.activity:activity-compose:1.5.0-rc01")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.9-beta")
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.foundation:foundation:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
}

configurations.all {
    exclude(group = "androidx.compose.material")
}

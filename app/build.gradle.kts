@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kspAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.serialization)
}

android {
    namespace = "kr.io.etri"
    compileSdk = 34

    testOptions {
        emulatorSnapshots {
            unitTests.isReturnDefaultValues = true
            enableForTestFailures = true
            maxSnapshotsForTestFailures = 3
            compressSnapshots = false
        }
    }
    defaultConfig {
        applicationId = "kr.io.etri"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "kr.io.etri.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField(
            "String",
            "API_KEY",
            "\"${libs.versions.api.key.get()}\""
        )
        buildConfigField(
            "String",
            "BASE_URL",
            "\"${libs.versions.base.url.get()}\""
        )


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))

    // UI Dependencies
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)

    implementation(libs.material3)
    implementation(libs.compose.navigation)
    // liveData
    implementation(libs.compose.livedata)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.junit.ktx)

    // mockito java 기반
    testImplementation(libs.mockito)
    // mockk kotlin 기반
    testImplementation(libs.mockk)


    // junit
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)


    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)


    // Appcompat 앱의 API 레벨 호환성을 해결한다.
    implementation(libs.appcompat)

    // constraint - layout
    implementation(libs.constraintlayout)

    // converter
    implementation(libs.moshi)
    implementation(libs.converter.moshi)
    ksp(libs.moshi.kotlin.codegen)

    //  retrofit & okhttp3
    implementation(libs.retrofit)
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging)

    // hilt
    implementation(libs.hilt)

    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)

    // hilt 를 사용하기 위한 dependencies
    ksp(libs.room.compiler)
    ksp(libs.hilt.android.compiler)

    kspTest(libs.hilt.android.compiler)
    kspAndroidTest(libs.hilt.android.compiler)

    // hilt navigation
    implementation(libs.hilt.navigation)

    // paging
    implementation(libs.paging)

    //coroutines
    implementation(libs.coroutines)
    testImplementation(libs.coroutines.test)

    // vico
    implementation(libs.vico)
    implementation(libs.vico.compose)

    // Data-store
    implementation(libs.datastore.preferences)

    // kotlin - serialization
    implementation(libs.kotlin.serialization)

    // permission
    implementation(libs.accompanist.permissions)


    // rxjava
    implementation(libs.rxjava)
}
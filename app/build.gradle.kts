plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.dahuka_devmobileapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.dahuka_devmobileapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":app:common"))
    implementation(project(":app:dang_nhap"))
    implementation(project(":app:dang_ky"))
    implementation(project(":app:xem_san_pham"))
    implementation(project(":app:gio_hang"))
    implementation(project(":app:quen_mk"))
    implementation(project(":app:ql_don_hang"))
    implementation(project(":app:so_dia_chi"))
    implementation(project(":app:quan_ly_thong_tin_ca_nhan"))

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

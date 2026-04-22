plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.common"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

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
    lint {
        targetSdk = 36
    }
    testOptions {
        targetSdk = 36
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    // Retrofit - gọi API (Dùng api để các module khác có thể sử dụng được retrofit types)
    api(libs.retrofit)
    // Gson - chuyển JSON ↔ Kotlin object
    api(libs.retrofit.converter.gson)
    // OkHttp - hỗ trợ interceptor (bỏ qua cảnh báo ngrok)
    api(libs.okhttp)
    api(libs.okhttp.logging.interceptor)
}

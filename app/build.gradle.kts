plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 33
        targetSdk = 34
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
    buildFeatures {
        compose = true
    }
    // ViewBinding 활성화
    viewBinding {
        enable = true  // Kotlin DSL에서 'enabled' 대신 'enable' 사용
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.7.10")  // Kotlin 최신 버전
    implementation ("androidx.appcompat:appcompat:1.6.1")  // 최신 버전으로 사용
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")  // 최신 버전 확인 후 사용
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // ConstraintLayout 상대적으로 화면 배치
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Retrofit 기본 의존성 (REST API 통신)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // JSON 파싱을 위한 Gson 컨버터
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")  // Glide 컴파일러
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
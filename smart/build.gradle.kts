plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "cn.lsmya.smart"
    compileSdk = 33

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    //kotlin携程
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    //图片选择(https://github.com/lucksiege/pictureselector)
    implementation("io.github.lucksiege:pictureselector:v3.10.7")
    implementation("io.github.lucksiege:compress:v3.10.7")
    implementation("io.github.lucksiege:ucrop:v3.10.7")
    implementation("io.github.lucksiege:camerax:v3.10.7")
    //图片加载(https://github.com/bumptech/glide)
    implementation("com.github.bumptech.glide:glide:4.14.2")
    ksp("com.github.bumptech.glide:compiler:4.14.2")
    //https://github.com/greenrobot/EventBus
    implementation("org.greenrobot:eventbus:3.3.1")
    //网络请求（https://github.com/liujingxing/rxhttp）
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.github.liujingxing.rxhttp:rxhttp:3.0.1")
    ksp("com.github.liujingxing.rxhttp:rxhttp-compiler:3.0.1")
    implementation("com.github.liujingxing.rxhttp:converter-moshi:3.0.1")
    //json解析
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
    //https://github.com/li-xiaojun/XPopup
    //https://github.com/li-xiaojun/XPopupExt
    implementation("com.github.li-xiaojun:XPopup:2.9.19")
    //智能刷新
    implementation("io.github.scwang90:refresh-layout-kernel:2.0.5")  //核心必须依赖
    implementation("io.github.scwang90:refresh-header-classics:2.0.5")  //经典刷新头
    implementation("io.github.scwang90:refresh-footer-classics:2.0.5")  //经典加载


}
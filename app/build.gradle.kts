plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
}

android {
    namespace = "com.armaganaysu.kotlincountries"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.armaganaysu.kotlincountries"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}
var lifecycle_version = "2.6.2"
var supportVersion = "28.0.0"
var retrofitVersion = "2.9.0"
var glideVersion = "4.15.0"
var room_version = "2.5.2"
var navVersion = "2.7.2"
var preferencesVersion = "1.2.1"
dependencies {

    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    //room
    implementation ("androidx.room:room-runtime:$room_version")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    ksp ("androidx.room:room-compiler:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")



    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    
    //coroutines for threads
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation ("androidx.fragment:fragment-ktx:1.5.7")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //rxJava
    implementation("androidx.room:room-rxjava2:$room_version")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("io.reactivex.rxjava2:rxkotlin:2.4.0")

    //glide
    implementation ("com.github.bumptech.glide:glide:$glideVersion")

    //noinspection GradleCompatible
    implementation ("com.android.support:palette-v7:$supportVersion")
    //noinspection GradleCompatible
    implementation ("com.android.support:design:$supportVersion")

    //preference
    implementation ("androidx.preference:preference:$preferencesVersion")

    implementation ("androidx.preference:preference-ktx:1.2.0")
}
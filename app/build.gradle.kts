plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.alirsyad.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.alirsyad.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 300
        versionName = "2.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            buildConfigField("String", "baseUrl", "\"https://dib.alirsyadbandung.sch.id/api/\"")
        }

        getByName("debug") {
            buildConfigField("String", "baseUrl", "\"https://dib.alirsyadbandung.sch.id/api/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.21")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    implementation("androidx.activity:activity-ktx:1.9.3")

    // LifeCycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    // Hilt - using KSP
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-android-compiler:2.51.1")
    ksp("androidx.hilt:hilt-compiler:1.2.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:3.12.1")

    // RxJava
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.10")
    implementation("com.jakewharton.rxbinding3:rxbinding:3.0.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:31.0.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    // Chucker
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    // Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // Lottie
    implementation("com.airbnb.android:lottie:3.4.0")

    // Room - using KSP
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-rxjava2:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // Encrypted Shared Preferences
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // PDF Viewer
    implementation("com.github.barteksc:android-pdf-viewer:3.0.0-beta.5")

    // YouTube Player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:13.0.0")

    // Expandable Layout
    implementation("com.github.cachapa:ExpandableLayout:2.9.2")

    // Draggable View
    implementation("com.github.hyuwah:DraggableView:1.0.0")

    // ACRA
    implementation("ch.acra:acra-toast:5.8.4")

    // Circle Image View
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // MultiSnap RecyclerView
    implementation("com.github.takusemba:multisnaprecyclerview:2.0.1")

    // Glide - using KSP
    implementation("com.github.bumptech.glide:glide:4.12.0")
    ksp("com.github.bumptech.glide:compiler:4.12.0")

    // Chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Android 12
    implementation("androidx.work:work-runtime-ktx:2.7.1")

    // Tooltips
    implementation("com.github.douglasjunior:android-simple-tooltip:1.1.0")

    // Jsoup
    implementation("org.jsoup:jsoup:1.10.3")

    // MathJax
    implementation("com.github.frhnfrq:MathView:1.2")
    implementation("jp.wasabeef:glide-transformations:4.3.0")
    implementation("com.github.worker8:RadioGroupPlus:1.0.1")

    // Gson
    implementation("com.google.code.gson:gson:2.9.0")

    // UI Library
    implementation("io.coil-kt:coil:1.2.0")
    implementation("org.ocpsoft.prettytime:prettytime:4.0.4.Final")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("com.ismaeldivita.chipnavigation:chip-navigation-bar:1.3.4")
    implementation("com.akexorcist:round-corner-progress-bar:2.1.2")
    implementation("com.tbuonomo:dotsindicator:4.2")
    implementation("net.orandja.shadowlayout:shadowlayout:1.0.1")
    implementation("com.squareup.picasso:picasso:2.71828")


    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.bsaitmattendance"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bsaitmattendance"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    //// for google sheet
    packagingOptions{
        exclude ("META-INF/DEPENDENCIES")
        exclude ("META-INF/LICENSE.txt")
        exclude ("META-INF/LICENSE")
        exclude ("META-INF/NOTICE.txt")
        exclude ("META-INF/NOTICE")
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

    buildFeatures{
        viewBinding=true
    }
}



dependencies {


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.sdp.android)
    implementation(libs.circleimageview)
    implementation(libs.firebase.firestore.ktx)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.apache.commons:commons-csv:1.8")
    implementation ("com.intuit.sdp:sdp-android:1.1.1")
    implementation ("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.github.marlonlom:timeago:4.0.3")
    implementation ("com.google.firebase:firebase-storage:20.3.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

}
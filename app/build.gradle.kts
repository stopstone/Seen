import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

val localPropertiesFile = rootProject.file("local.properties")
val properties = Properties()
properties.load(FileInputStream(localPropertiesFile))

android {
    namespace = "com.dev_stopstone.seenapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dev_stopstone.seenapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CLIENT_ID", "\"${properties["client.id"]}\"")
        buildConfigField("String", "CLIENT_SECRET", "\"${properties["client.secret"]}\"")

        val naverClientId = properties["client.id"] as? String ?: ""
        val naverClientSecret = properties["client.secret"] as? String ?: ""

        manifestPlaceholders["NAVER_API_ID"] = naverClientId
        manifestPlaceholders["NAVER_API_SECRET"] = naverClientSecret

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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    //noinspection GradleDependency
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Swipe
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage")

    // Network
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.naver.maps:map-sdk:3.17.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")

}
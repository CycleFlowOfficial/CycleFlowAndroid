plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlinx.kover")
}

android {
    namespace = "com.cycleflowofficial.data"
    compileSdk = 34
    
    defaultConfig {
        minSdk = 26
        
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        
        
    }
    
    buildFeatures {
        buildConfig = true
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
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
        jvmTarget = "17"
    }
    
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

hilt {
    enableAggregatingTask = true
}

dependencies {
    implementation(project(":domain"))
    
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.gson)
    implementation(libs.hilt.android.v249)
    implementation(libs.play.services.tasks)
    ksp(libs.hilt.compiler)
    
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    
    // region Hilt x Worker https://developer.android.com/training/dependency-injection/hilt-jetpack#workmanager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work.v110)
    ksp(libs.androidx.hilt.compiler.v110)
    
    
    testImplementation("androidx.arch.core:core-testing:2.1.0") {
        // Removes the Mockito dependency bundled with arch core (wtf android ??)
        exclude("org.mockito", "mockito-core")
    }
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.assertk.jvm)
}
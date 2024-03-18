plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlinx.kover")
}

android {
    namespace = "com.cycleflowofficial.cycleflowandroid"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.cycleflowofficial.cycleflowandroid"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true
    }
}

dependencies {
    implementation(project(":ui"))
    implementation(project(":domain"))
    implementation(project(":data"))
    
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // region Hilt x Worker https://developer.android.com/training/dependency-injection/hilt-jetpack#workmanager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
}

dependencies {
    kover(project(":data"))
    kover(project(":domain"))
    kover(project(":ui"))
}

koverReport {
    filters {
        excludes {
            annotatedBy(
                "dagger.Module",
                "dagger.internal.DaggerGenerated",
                "androidx.room.Database",
            )
            packages(
                "hilt_aggregated_deps.*", // Hilt: GeneratedInjectors (NOT annotated by DaggerGenerated)
                // ViewBinding
            )
            classes(
                // COMMON
                // Hilt
                "*_*Factory\$*",
                "hilt_aggregated_deps.*",
                // Gradle Generated
                
                
                // DATA
                // Room
                "*_Impl",
                "*_Impl\$*",
                
                "*AppDatabase\$*",
                
                
                // UI XML
                // Utils

                // Android UI
                "*MainApplication",
                "*MainApplication\$*",
                "*Fragment",
                "*Fragment\$*",
                "*Activity",
                "*Activity\$*",
                "*Adapter",
                "*Adapter\$*",
            )
        }
    }
}
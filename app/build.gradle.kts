//apply plugin: 'com.android.application'
//apply plugin: 'kotlin-android'
//apply plugin: 'kotlin-kapt'
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "ru.home.government"
    buildToolsVersion = "30.0.3"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    packagingOptions {
        resources {
            excludes += "META-INF/{AL2.0,LGPL2.1}"
        }
    }

    defaultConfig {
        applicationId = "ru.home.government"
        minSdk = libs.versions.minSdkVersion.get().toInt()
        targetSdk = libs.versions.targetSdkVersion.get().toInt()
        versionCode = 14
        versionName = "1.1.3"
        testInstrumentationRunner = "ru.home.government.MyCustomTestRunner"

/*        javaCompileOptions {
            annotationProcessorOptions {
                arguments << ["dagger.gradle.incremental": "true"]
            }
        }*/

    }

    flavorDimensions("default")

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    buildTypes {
        getByName("release")  {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    testOptions {
        animationsDisabled = true
        unitTests {
            isReturnDefaultValues = true
        }
    }
    productFlavors {
        create("beta") {
            applicationIdSuffix = ".beta"
        }
    }
    lint {
        abortOnError = false
    }
//    resolutionStrategy {
//        exclude group: "org.jetbrains.kotlinx", module: "kotlinx-coroutines-debug"
//    }
//    applicationVariants.configureEach { variant ->
//        variant.outputs.each { output ->
//            variant.outputs.all {
//                val date = Date().format('dd-MM-YYYY')
//                outputFileName = "RussianGovernment-${variant.versionName}(${variant.versionCode})-${date}.apk"
//            }
//        }
//    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    /* androidx libs*/
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")
    implementation("androidx.activity:activity-ktx:1.4.0") /* TODO updating this libs will break search 3rd party lib - expect to clone and maintain this 3rd party lib */
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    implementation("androidx.test.espresso:espresso-idling-resource:3.4.0")
    implementation("androidx.browser:browser:1.4.0")
    /* room libs */
    implementation("androidx.room:room-runtime:2.4.2")
    implementation("androidx.room:room-ktx:2.4.2")
    /* coroutines */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$libs.versions.kotlin.get()")
    /* kodein */
    implementation("org.kodein.di:kodein-di-generic-jvm:6.2.0")
    implementation("org.kodein.di:kodein-di-framework-android-x:6.2.0")
    /* retrofit */
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    /* shimmer effect */
    implementation("com.github.skydoves:androidveil:1.1.3")
    /* unit tests */
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("org.mockito:mockito-inline:3.12.4")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    /* android tests */
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito:2.28.1")
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    androidTestImplementation("org.mockito:mockito-core:3.12.4")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    /* DI */
    implementation("com.google.dagger:dagger:2.42")
    implementation("com.google.dagger:dagger-android-support:2.42")
    /* kapt */
    kapt("androidx.room:room-compiler:2.4.2")
    kapt("androidx.lifecycle:lifecycle-compiler:2.5.0")
    kapt("com.google.dagger:dagger-compiler:2.42")
    /* kapt android tests */
    kaptAndroidTest("com.google.dagger:dagger-compiler:2.42")
    /* others */
    implementation("io.reactivex.rxjava2:rxandroid:2.0.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.dropbox.mobile.store:store4:4.0.2-KT15") {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core-jvm")
    }
    implementation("androidx.test.espresso:espresso-idling-resource:3.4.0")
    implementation("com.github.Ferfalk:SimpleSearchView:0.2.0")

    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$libs.versions.kotlin.get()")

    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1") {
        // conflicts with mockito due to direct inclusion of byte buddy
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }


    compileOnly("javax.annotation:jsr250-api:1.0")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.8.1")
}

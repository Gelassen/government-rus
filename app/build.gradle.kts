plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "ru.home.government"
    buildToolsVersion = libs.versions.buildToolsVersion.get().toString()
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

    flavorDimensions.add("default")

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
    implementation(libs.appCompat)
    implementation(libs.core.ktx)
    implementation(libs.constrainLayout)
    implementation(libs.recyclerView)
    implementation(libs.paging.runtime)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.activity.ktx) /* TODO updating this libs will break search 3rd party lib - expect to clone and maintain this 3rd party lib */
    implementation(libs.fragment.ktx)
    implementation(libs.espresso.idling.resource)
    implementation(libs.browser)
    /* room libs */
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    /* coroutines */
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlin.stdlib.jdk7)
    /* kodein */
    implementation(libs.kodein.di.generic.jvm)
    implementation(libs.kodein.di.framework.android.x)
    /* retrofit */
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.adapter.rxjava2)
    /* shimmer effect */
    implementation(libs.androidVeil)
    /* unit tests */
    testImplementation(libs.mockwebserver)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.core.testing)
    /* android tests */
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.espresso.intents)
    androidTestImplementation(libs.androidxJunit)
    androidTestImplementation(libs.testRunner)
    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.testRules)
    androidTestImplementation(libs.dexmaker.mockito)
    androidTestImplementation(libs.androidTest.mockito.kotlin)
    androidTestImplementation(libs.androidTest.mockito.core)
    androidTestImplementation(libs.androidTest.mockwebserver)
    androidTestImplementation(libs.androidTest.testCore)
    androidTestImplementation(libs.androidTest.junit)
    androidTestImplementation(libs.androidTest.kotlinx.coroutines.test) {
        // conflicts with mockito due to direct inclusion of byte buddy
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }
    /* DI */
    implementation(libs.dagger)
    implementation(libs.dagger.android.support)
    /* kapt */
    kapt(libs.kapt.room.compiler)
    kapt(libs.kapt.lifecycle.compiler)
    kapt(libs.kapt.dagger.compiler)
    /* kapt android tests */
    kaptAndroidTest(libs.kaptAndroidTests.dagger.compiler)
    /* others */
    implementation(libs.rxandroid)
    implementation(libs.logging.interceptor)
    implementation(libs.dropbox.store4) {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core-jvm")
    }
    implementation(libs.simpleSearchView)

    testRuntimeOnly(libs.testRuntimeOnly.kotlin.reflect)

    compileOnly(libs.compileOnly.jsr250.api)

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.8.1")
}

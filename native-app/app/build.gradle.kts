plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.gasczoology.invertebratelab"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.gasczoology.invertebratelab"
        minSdk = 24
        targetSdk = 36

        // Preserve the currently accepted release identity during migration.
        // Do not change without explicit release approval.
        versionCode = 18900
        versionName = "1.8.9"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2026.09.00")

    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.animation:animation") // native stage crossfade
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.11.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.11.0")
    implementation("androidx.navigation:navigation-compose:2.9.8")
    implementation("androidx.datastore:datastore-preferences:1.2.1")
    implementation("com.google.code.gson:gson:2.13.2")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
}

// Generated, rights-unverified Pila artwork is packaged ONLY in debug builds.
// Release APK/AAB variants must never contain these editorial references.
val extractPilaReview by tasks.registering(Copy::class) {
    val archive = rootProject.file("../pila_android_review_12_panels.zip")
    onlyIf { archive.isFile }
    from(provider { zipTree(archive) }) {
        include("native-app/app/src/main/assets/review_only/pila/generated_12/*.png")
        eachFile { path = path.removePrefix("native-app/app/src/main/assets/") }
        includeEmptyDirs = false
    }
    into(layout.buildDirectory.dir("generated/pilaDebugAssets"))
}
android.sourceSets.getByName("debug").assets.srcDir(layout.buildDirectory.dir("generated/pilaDebugAssets").get().asFile)
tasks.matching { it.name == "mergeDebugAssets" }.configureEach { dependsOn(extractPilaReview) }

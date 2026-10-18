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

// Extract all twelve PNGs regardless of their paths inside the uploaded archive.
// Fail visibly for missing, duplicate, or malformed images; debug only.
val extractPilaReview = tasks.register("extractPilaReview") {
    val archive = rootProject.file("../pila_android_review_12_panels.zip")
    val destination = layout.buildDirectory.dir("generated/pilaDebugAssets")
    inputs.file(archive)
    outputs.dir(destination)
    doLast {
        val pngs = zipTree(archive).files.filter { it.extension.equals("png", true) }.sortedBy { it.name }
        require(pngs.size == 12) { "Expected 12 Pila PNG panels; found ${pngs.size}" }
        val hashes = pngs.map { java.security.MessageDigest.getInstance("SHA-256").digest(it.readBytes()).toList() }
        require(hashes.distinct().size == 12) { "Pila archive has duplicate PNG images" }
        val target = destination.get().asFile.resolve("review_only/pila/generated_12")
        target.mkdirs()
        target.listFiles()?.forEach { it.delete() }
        pngs.forEachIndexed { index, source ->
            val bytes = source.readBytes()
            require(bytes.size > 1024 && bytes.take(8) == listOf(137,80,78,71,13,10,26,10).map { it.toByte() }) {
                "Invalid Pila PNG: ${source.name}"
            }
            target.resolve("%02d_pila_panel_REVIEW.png".format(index + 1)).writeBytes(bytes)
        }
    }
}
android.sourceSets.getByName("debug").assets.srcDir(layout.buildDirectory.dir("generated/pilaDebugAssets").get().asFile)
tasks.matching { it.name == "mergeDebugAssets" }.configureEach { dependsOn(extractPilaReview) }

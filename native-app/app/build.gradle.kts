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

// Package the twelve individually uploaded PNGs as debug-only editorial assets.
// Never include rights-unverified images in release APK/AAB variants.
val stagePilaIndividualReview = tasks.register("stagePilaIndividualReview") {
    val names = listOf(
        "01_shell_ventral_REVIEW.png", "02_operculum_views_REVIEW.png",
        "03_shell_dorsal_REVIEW.png", "04_animal_front_REVIEW.png",
        "05_nervous_system_REVIEW.png", "06_buccal_mass_REVIEW.png",
        "07_digestive_system_REVIEW.png", "08_respiratory_system_REVIEW.png",
        "09_circulatory_system_REVIEW.png", "10_male_reproductive_REVIEW.png",
        "11_female_reproductive_REVIEW.png", "12_excretory_system_REVIEW.png",
    )
    val files = names.map { rootProject.file("../" + it) }
    val destination = layout.buildDirectory.dir("generated/pilaDebugAssets")
    inputs.files(files)
    outputs.dir(destination)
    doLast {
        val target = destination.get().asFile.resolve("review_only/pila/generated_12")
        target.mkdirs()
        target.listFiles()?.forEach { it.delete() }
        val hashes = mutableSetOf<String>()
        names.zip(files).forEach { (name, source) ->
            require(source.isFile) { "Missing uploaded Pila image: " + source.path }
            val bytes = source.readBytes()
            require(bytes.size > 1024 && bytes.take(8) == listOf(137,80,78,71,13,10,26,10).map { it.toByte() }) {
                "Invalid PNG: " + name
            }
            val hash = bytes.contentHashCode().toString()
            require(hashes.add(hash)) { "Duplicate Pila PNG image: " + name }
            target.resolve(name).writeBytes(bytes)
        }
    }
}
android.sourceSets.getByName("debug").assets.srcDir(layout.buildDirectory.dir("generated/pilaDebugAssets").get().asFile)
tasks.matching { it.name == "mergeDebugAssets" }.configureEach { dependsOn(stagePilaIndividualReview) }

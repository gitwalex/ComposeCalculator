import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    id("maven-publish")
}
kotlin {
    compilerOptions {
        freeCompilerArgs =
            listOf(
                "-Xjavac-arguments='-Xlint:unchecked -Xlint:deprecation'",
                "-opt-in=kotlin.RequiresOptIn",
                "-Xexplicit-backing-fields",
                "-Xcontext-parameters",

                )
        jvmTarget = JvmTarget.JVM_21
        languageVersion.set(KotlinVersion.KOTLIN_2_3)
    }
}

android {
    namespace = "com.gerwalex.calculator"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true // Wichtig für Robolectric + Compose
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

}

dependencies {

    val composeBom = project.dependencies.platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.material)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)


    testImplementation(libs.junit)
    testImplementation(libs.slf4j.simple)
    testImplementation(libs.bundles.test)
    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
    testImplementation(libs.roboelectric)
    testImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.test.espresso)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.agent)


}
publishing {

    publications {
        register<MavenPublication>("release") {
            groupId = "github.gitwalex.com"
            artifactId = "ComposeCalculator"
            version = "0.0.5"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
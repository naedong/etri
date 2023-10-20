buildscript {
    dependencies {
        classpath(libs.hilt.gradle)
    }
}
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid)  apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.kspAndroid) apply false
    alias(libs.plugins.serialization) apply false
}
true
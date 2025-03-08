buildscript {
    repositories {
        google()  // Certifique-se de que o repositório Google esteja incluído
        mavenCentral() // E o Maven Central
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0") // Verifique a versão mais recente
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}
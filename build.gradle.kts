// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt)
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        config = files("$rootDir/build-logic/quality/detekt/detekt-config.yml")
        baseline = file("$rootDir/build-logic/quality/detekt/baseline.xml")
        ignoreFailures = true
    }
}

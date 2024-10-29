package io.filmtime.gradle.plugins

import com.android.build.api.dsl.ApplicationExtension
import io.filmtime.gradle.configureFlavors
import io.filmtime.gradle.configureGooglePlayPublish
import io.filmtime.gradle.configureGooglePlayPublishFlavors
import io.filmtime.gradle.configureKotlinAndroid
import io.filmtime.gradle.configureVersionCode
import io.filmtime.gradle.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class TvApplicationPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")

        apply("io.filmtime.gradle.android.application.compose")
        apply("io.filmtime.gradle.android.hilt")

        apply("me.moallemi.advanced-build-version")
      }

      extensions.configure<ApplicationExtension> {
        configureKotlinAndroid()

        defaultConfig {
          targetSdk = 34
        }

        configureVersionCode(this)

        configureFlavors(this)

        configureGooglePlayPublishFlavors(this)

        signingConfigs {
          getByName("debug") {
            storeFile = rootProject.file("release/filmtime-debug.jks")
            storePassword = "keystorepassword"
            keyAlias = "filmtime"
            keyPassword = "aliaspassword"
          }

          create("release") {
            if (rootProject.file("release/filmtime-release.jks").exists()) {
              storeFile = rootProject.file("release/filmtime-release.jks")
              storePassword = properties["FILM_TIME_RELEASE_KEYSTORE_PASSWORD"]?.toString() ?: ""
              keyAlias = "key0"
              keyPassword = properties["FILM_TIME_RELEASE_KEY_PASSWORD"]?.toString() ?: ""
            }
          }
        }

        buildTypes {
          debug {
            signingConfig = signingConfigs.findByName("debug")
          }

          release {
            signingConfig = signingConfigs.findByName("release")
            isShrinkResources = false
            isMinifyEnabled = false
            proguardFiles("proguard-rules.pro")
          }
        }
      }

      dependencies {
        add("implementation", libs.findLibrary("androidx-tv-foundation").get())
        add("implementation", libs.findLibrary("androidx-tv-material").get())
      }

      configureGooglePlayPublish()
    }
  }
}

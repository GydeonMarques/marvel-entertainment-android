package app.dependencies

import org.gradle.api.JavaVersion


object Versions {
    const val minSdkVersion = 21
    const val targetSdkVersion = 32
    const val compileSdkVersion = 32
}

object Compile {
    val javaVersion = JavaVersion.VERSION_11
}

object Libs {

    object Kotlin {
        private const val kotlin_version = "1.3.72"
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.7.0"
        const val appCompat = "androidx.appcompat:appcompat:1.4.1"
        const val material = "com.google.android.material:material:1.5.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val legacySupport = "'androidx.legacy:legacy-support-v4:1.0.0'"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

        object Lifecycle {
            private const val version = "2.4.1"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Navigation {
            private const val version = "2.4.1"
            const val ktx = "androidx.navigation:navigation-ui-ktx:$version"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }
    }

    object Modules {
        const val data = ":data"
        const val domain = ":domain"
        const val storage = ":storage"
    }

    object Log {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }

    object Lottie {
        const val lottie = "com.airbnb.android:lottie:3.4.4"
    }

    object Room {
        private const val version = "2.4.2"
        const val ktx = "androidx.room:room-ktx:$version"
        const val room = "androidx.room:room-runtime:$version"
        const val compile = "androidx.room:room-compiler:$version"
        const val rxjava = "androidx.room:room-rxjava2:$version"
    }

    object Glide {
        private const val version = "4.12.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compile = "com.github.bumptech.glide:compiler:$version"
    }

    object OKHTTP3 {
        private const val version = "4.9.0"
        const val okhttp3 = "com.squareup.okhttp3:okhttp"
        const val bom = "com.squareup.okhttp3:okhttp-bom:$version"
        const val logging = "com.squareup.okhttp3:logging-interceptor"
    }

    object Dagger {
        private const val version = "2.23.2"
        const val dagger = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
    }

    object RXNetwork {
        const val rxNetwork = "com.github.pwittchen:reactivenetwork-rx2:3.0.8"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val jacksonConverter = "com.squareup.retrofit2:converter-jackson:$version"
    }

    object Coroutines {
        private const val version = "1.6.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val ext = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }
}
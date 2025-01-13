This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

## Prerequisites

Add your [TMDB](https://www.themoviedb.org/) API key in the `local.properties` file:
```
TMDB_API_KEY=YOUR_API_KEY
```

## Libraries
* [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) - Simplify the development of cross-platform projects and reduce the time spent writing and maintaining the same code for different platforms.
* [Ktor Client](https://ktor.io/docs/welcome.html) - Ktor includes a multiplatform asynchronous HTTP client, which allows you to make requests and handle responses.
* [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) - Kotlin multiplatform / multi-format reflectionless serialization
* [Koin](https://github.com/InsertKoinIO/koin) - is a lightweight dependency injection framework for Kotlin & Kotlin Multiplatform.
* [Kotlin coroutines](https://developer.android.com/kotlin/coroutines) - Executing code asynchronously.
* [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - is a state-holder observable flow that emits the current and new state updates to its collectors.
* [Coil-compose](https://coil-kt.github.io/coil/compose/) - An image loading library for Android backed by Kotlin Coroutines.
* [Material3 CenterAlignedTopAppBar](https://developer.android.com/develop/ui/compose/components/app-bars) - Provides access to key tasks and information. Generally hosts a title, core action items, and certain navigation items.
* [SKIE](https://github.com/touchlab/SKIE) - A tool for Kotlin Multiplatform development that enhances the Swift API published from Kotlin.
* [moko-resources](https://github.com/icerockdev/moko-resources) - provides access to the resources on macOS, iOS, Android the JVM and JS/Browser with the support of the default system localization.
* [BuildKonfig](https://github.com/yshrsmz/BuildKonfig) - BuildConfig for Kotlin Multiplatform Project.
* [Napier](https://github.com/AAkira/Napier) - logger library for Kotlin Multiplatform project.

## Licence
    MIT License

    Copyright (c) 2025 Mohammadali Rezaei

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.

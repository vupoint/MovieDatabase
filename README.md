![logo](arts/logo.png)

# 🎥 Movie Database

![Android CI](https://github.com/hide1202/MovieDatabase/workflows/Android%20CI/badge.svg?branch=develop)
[![codecov](https://codecov.io/gh/hide1202/MovieDatabase/branch/develop/graph/badge.svg)](https://codecov.io/gh/hide1202/MovieDatabase)

- Application for the practice of android development using Movie Database API
- This repository is my Android lab. I'm experimenting with a lot of new libraries and trying out different things.

## 📋 Prerequisites

- Android Studio Rabbit 2 | 2026.2.2 Patch 1 or a newer version that supports AGP 9.5.
- JDK 17 for Gradle, locally and in CI.
- Android SDK Platform 35 and Build Tools 36.0.0. Set your SDK path in the ignored `local.properties` file or `ANDROID_HOME`.
- Use the checked-in Gradle 9.7.0 wrapper. The build uses AGP 9.5.1, Kotlin and the Compose compiler 2.4.21, and KSP 2.3.13.

Gradle 9.7.0 is within [Kotlin's supported Gradle range](https://kotlinlang.org/docs/gradle-configure-project.html) and meets [AGP 9.5's minimum requirement](https://developer.android.com/build/releases/agp-9-5-0-release-notes). Android modules use AGP's built-in Kotlin support. Hilt, Dagger, and Room use KSP for code generation.

## 📸 Screenshots
| splash | home | detail | search |
|---|---|---|---|
| ![splash](arts/splash.jpg) | ![home](arts/home.jpg) | ![detail](arts/detail.jpg) | ![search](arts/search.jpg) |


## 🛠️ Tech Stack
- Kotlin, and Coroutines, Flow
- [Compose](https://developer.android.com/compose): a modern toolkit for building native UI using Kotlin
- Jetpack
  - [Lifecycle](): for building lifecycle-aware components
  - [Navigation](https://developer.android.com/guide/navigation): manage a application navigation flow
  - [App Startup](https://developer.android.com/topic/libraries/app-startup): for initializing components
  - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview): for loading and display pages of data from a larger dataset
  - [Room](https://developer.android.com/training/data-storage/room): for data storage persistence
- Architecture
  - MVVM Architecture (Model - View - ViewModel)
  - UDF (Unidirectional Data Flow)
- [Retrofit (w/OkHttp)](https://github.com/square/retrofit): for making network requests and fetching data
- [Moshi](https://github.com/square/moshi): for serialization/deserialization JSON request, or response
- [Glide](https://bumptech.github.io/glide/): for loading images of remote resources
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): for depedency injection
- [Timber](https://github.com/JakeWharton/timber): for logging

## ▶️ How to Run

- Create a file in the root of project with `external.properties` name.
- And write following property with your api key.

```
movie.database.api.key=YOUR_API_KEY
```

## Modules
- You can see the structure of the module in [MODULES.md](./MODULES.md).

## 🚀 Future Plans

We are planning to extend this project to support **Compose Multiplatform**, 
enabling it to run seamlessly across multiple platforms like Android, iOS, and desktop. 

Stay tuned for updates!

## ⭐ Support

If you find this project helpful or interesting, please give it a **Star**! 🌟  
Your support motivates us to improve and add new features.

## References

- The Movie Database API : https://developers.themoviedb.org/3/
- Vector icons : https://www.flaticon.com/kr/

# Savour

A small Android app for exploring packaged food products using the free [Open Food Facts](https://world.openfoodfacts.org/) API.

## Features

- **Search** products by name, with photos, brand and Nutri-Score grade (A–E)
- **Product details**: large image, Nutri-Score, categories and ingredients
- **Favorites**: tap the heart to save a product; favorites work offline
- **Personal reviews**: give a product 1–5 stars and write a short note, stored on the device

## Tech stack

- Kotlin, Jetpack Compose (Material 3)
- Retrofit + kotlinx.serialization for the network
- Room for local favorites and reviews
- Coil for image loading
- Navigation Compose
- Gradle Kotlin DSL with a version catalog (`gradle/libs.versions.toml`)

## Project structure

```
com.example.savour
├── data
│   ├── remote      // Retrofit API + response models (DTOs)
│   ├── local       // Room entity, DAO, database
│   └── repository  // FoodRepository, Food model, mappers
├── ui
│   ├── list        // search screen + ViewModel
│   ├── detail      // detail screen, review form + ViewModel
│   ├── favorites   // favorites screen + ViewModel
│   ├── components  // NutriScoreBadge, RatingBar
│   └── theme
├── navigation      // NavGraph with bottom bar
├── SavourApp.kt    // builds the single FoodRepository
└── MainActivity.kt
```

## Running

1. Open the project in Android Studio.
2. Let Gradle sync.
3. Run the `app` configuration on an emulator or device with internet access.

No API key is needed. Open Food Facts asks apps to send a User-Agent identifying themselves, which is set in `NetworkModule.kt`. Searches only run when you press search on the keyboard, to respect their rate limit (about 10 searches a minute).

## Data credit

Product data and images come from Open Food Facts, available under the Open Database License.

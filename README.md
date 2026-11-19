# Savour 🍽️

**Savour** is an Android app that lets users search food products via the Open Food Facts API, save favourite items locally, and view detailed information with a rating bar and review form.

---

## Features
- 🔎 Search foods using the public Open Food Facts API.
- ⭐ Save favourite foods locally with Room.
- 📋 Detail view with a composable **RatingBar** and a **ReviewForm**.
- 🚦 Jetpack Compose UI and bottom navigation.
- 🏗️ Clean architecture – ViewModel + Repository pattern with Hilt DI.

---

## Architecture Overview
```
MainActivity
 └─ SavourNavGraph (Compose NavHost)
      ├─ SearchScreen   → FoodRepository → OpenFoodFactsApi (Retrofit)
      ├─ FavoritesScreen → FavoritesViewModel (Room)
      └─ DetailScreen    → DetailViewModel (Repo) → RatingBar & ReviewForm
```

- **UI layer** – composables under `ui/` (detail, favorites, components).
- **ViewModel layer** – `DetailViewModel`, `FavoritesViewModel` expose `StateFlow` to the UI.
- **Data layer** – `FoodRepository` mediates between `OpenFoodFactsApi` (remote) and `AppDatabase` (local).
- **DI** – Hilt provides repository, database, and ViewModel factories.

---

## Getting Started
```bash
# Clone the repo
git clone https://github.com/KA6TIKGIT/savour.git
cd savour
```
Open the project in Android Studio (Flamingo or newer) and let Gradle sync. Then run the app on an emulator or device.

---

## Running the App
```bash
./gradlew installDebug   # builds and installs the debug APK
```
The app launches with the Search tab selected. Use the bottom bar to switch to Favorites or tap a product to view its details.

---

## Testing
- Unit tests: `./gradlew test`
- Instrumented UI tests: `./gradlew connectedAndroidTest`

---

## Project Structure
```
savour/
 └─ app/src/main/java/com/example/savour/
      ├─ MainActivity.kt
      ├─ navigation/          # NavGraph & routes
      ├─ data/                # remote, local, repository
      └─ ui/                  # list, detail, favorites, components
```

---

## Contributing
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/<name>`).
3. Make changes, run tests, and commit.
4. Push and open a Pull Request.

---

## License
MIT – see the `LICENSE` file.

---

## Contact
**Author**: Amartya Sinha – amartya@example.com
**GitHub**: https://github.com/KA6TIKGIT/savour

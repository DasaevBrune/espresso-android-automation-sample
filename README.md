# Espresso Android Automation Sample

This is a portfolio project demonstrating robust Android UI automation using **Kotlin** and **Espresso**. The application consists of three screens (Login, List, Detail) designed specifically to showcase typical automation challenges like list scrolling, state persistence, and navigation validation.

## Tech Stack

- **Language**: Kotlin 1.9
- **UI Framework**: Android Views (XML), Activities
- **Testing**: Espresso 3.5, JUnit 4, AndroidX Test rules
- **Architecture**: Minimal MVC (to focus on automation target stability)

## Functionality

1.  **Login Activity**: Validates credentials (`demo` / `password`). Shows error on failure.
2.  **Items Activity**: Displays a scrollable list of 20 items.
3.  **Detail Activity**: View persistence demonstration. Saves "Favorite" status and "Notes" using `SharedPreferences`.

## Automation Practices Demonstrated

- **Deterministic IDs**: Every interactable view has a stable resource ID (`etUsername`, `rvItems`, etc.).
- **No `Thread.sleep`**: All synchronization is handled by Espresso's internal idling resources.
- **RecyclerView Actions**: using `espresso-contrib` for reliable scrolling and clicking on list items.
- **State Verification**: Tests ensure data persists correctly across app sessions.

## How to Run the App

1.  Open this project in **Android Studio Giraffe** (or newer).
2.  Sync Gradle.
3.  Select the `app` configuration.
4.  Run on an emulator or physical device.

## How to Run Tests

Execute the following command in terminal to run all instrumentation tests:

```bash
./gradlew connectedDebugAndroidTest
```

### Included Tests (`EspressoSystemTest.kt`)

1.  `login_success_navigates_to_items`: Happy path login flow.
2.  `login_invalid_shows_error_and_stays_on_login`: Error state validation.
3.  `click_item_opens_detail_with_correct_title`: Navigation data passing.
4.  `scroll_and_open_item_15`: Scrolling to off-screen elements.
5.  `favorite_persists_for_item`: End-to-end data persistence check.

Test run evidence

✅ 5/5 instrumentation tests passing (Pixel 6 emulator)

Report screenshot: /docs/test-report.png


## CI/CD Pipeline

A sample `.gitlab-ci.yml` is included to demonstrate how these tests could be run in a headless environment. 
*Note: Running Android emulators in CI requires hardware acceleration (KVH) which may require specific runner configuration.*

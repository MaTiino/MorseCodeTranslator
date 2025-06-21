# 🔥 Firebase Setup Guide

## 📋 Konfiguracja Firebase dla Morse Code Translator

### **Ważne: Bezpieczeństwo**
Plik `google-services.json` zawiera wrażliwe klucze API i **NIE** powinien być udostępniany publicznie. Z tego powodu został dodany do `.gitignore`.

### **Krok 1: Utwórz Projekt Firebase**
1. Przejdź do [Firebase Console](https://console.firebase.google.com/)
2. Kliknij "Dodaj projekt"
3. Wprowadź nazwę: `MorseCodeTranslator`
4. Wyłącz Google Analytics (opcjonalnie)
5. Kliknij "Utwórz projekt"

### **Krok 2: Dodaj Aplikację Android**
1. W Firebase Console kliknij ikonę Android
2. Wprowadź package name: `com.example.morsecodetranslator`
3. Wprowadź nickname: `Morse Code Translator`
4. Kliknij "Zarejestruj aplikację"

### **Krok 3: Pobierz Plik Konfiguracyjny**
1. Pobierz plik `google-services.json` z Firebase Console
2. **Zastąp** plik `app/google-services.json.template` pobranym plikiem
3. Lub skopiuj pobrany plik jako `app/google-services.json`

### **Krok 4: Włącz Usługi Firebase**

#### **Authentication**
1. W Firebase Console przejdź do "Authentication"
2. Kliknij "Rozpocznij"
3. W zakładce "Sign-in method" włącz:
   - **Email/Password**
   - **Google** (opcjonalnie)
4. Kliknij "Zapisz"

#### **Firestore Database**
1. W Firebase Console przejdź do "Firestore Database"
2. Kliknij "Utwórz bazę danych"
3. Wybierz "Start in test mode"
4. Wybierz lokalizację (najbliższą użytkownikom)
5. Kliknij "Gotowe"

### **Krok 5: Skonfiguruj Reguły Firestore**
1. W Firestore przejdź do zakładki "Rules"
2. Zastąp reguły następującym kodem:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /translations/{document} {
      allow read, write: if request.auth != null && 
        request.auth.uid == resource.data.userId;
    }
  }
}
```

3. Kliknij "Opublikuj"

### **Krok 6: Utwórz Użytkownika Testowego**
1. W Authentication przejdź do zakładki "Users"
2. Kliknij "Dodaj użytkownika"
3. Wprowadź:
   - **Email**: `a@a.pl`
   - **Hasło**: `ZAQ1@WSX`
4. Kliknij "Dodaj użytkownika"

### **Krok 7: Test Konfiguracji**
1. Uruchom aplikację w Android Studio
2. Zaloguj się używając danych testowych
3. Sprawdź czy kodowanie/dekodowanie działa
4. Sprawdź czy historia jest zapisywana

## 🔒 Bezpieczeństwo

### **Co NIE robić:**
- ❌ Nie udostępniaj `google-services.json` publicznie
- ❌ Nie commituj prawdziwych kluczy API do Git
- ❌ Nie umieszczaj kluczy w kodzie źródłowym

### **Co robić:**
- ✅ Używaj `.gitignore` do wykluczenia wrażliwych plików
- ✅ Udostępniaj tylko szablony konfiguracji
- ✅ Przekazuj klucze bezpiecznie (np. przez email, Slack, etc.)
- ✅ Regularnie rotuj klucze API

## 📁 Struktura Plików

```
app/
├── google-services.json          # Prawdziwy plik (ignorowany przez Git)
├── google-services.json.template # Szablon (w repozytorium)
└── ...
```

## 🚨 Troubleshooting

### **Problem: "google-services.json not found"**
**Rozwiązanie:**
1. Sprawdź czy plik jest w katalogu `app/`
2. Sprawdź czy plugin `com.google.gms.google-services` jest w build.gradle
3. Synchronizuj projekt z Gradle

### **Problem: Błąd logowania**
**Rozwiązanie:**
1. Sprawdź czy Authentication jest włączone
2. Sprawdź czy użytkownik testowy istnieje
3. Sprawdź reguły Firestore

### **Problem: Historia nie ładuje się**
**Rozwiązanie:**
1. Sprawdź połączenie internetowe
2. Sprawdź reguły Firestore
3. Sprawdź czy użytkownik jest zalogowany

## 📞 Wsparcie

Jeśli masz problemy z konfiguracją:
1. Sprawdź logi w Android Studio
2. Sprawdź Firebase Console
3. Sprawdź dokumentację Firebase
4. Skontaktuj się z zespołem deweloperskim

---

**Ostatnia aktualizacja**: 2024  
**Wersja**: 1.0 
package com.example.morsecodetranslator;

import java.util.HashMap;

public class MorseCodeTranslator {
    private static final HashMap<Character, String> morseMap = new HashMap<>();
    private static final HashMap<String, Character> reverseMorseMap = new HashMap<>();

    static {
        morseMap.put('A', ".-");
        morseMap.put('B', "-...");
        morseMap.put('C', "-.-.");
        morseMap.put('D', "-..");
        morseMap.put('E', ".");
        morseMap.put('F', "..-.");
        morseMap.put('G', "--.");
        morseMap.put('H', "....");
        morseMap.put('I', "..");
        morseMap.put('J', ".---");
        morseMap.put('K', "-.-");
        morseMap.put('L', ".-..");
        morseMap.put('M', "--");
        morseMap.put('N', "-.");
        morseMap.put('O', "---");
        morseMap.put('P', ".--.");
        morseMap.put('Q', "--.-");
        morseMap.put('R', ".-.");
        morseMap.put('S', "...");
        morseMap.put('T', "-");
        morseMap.put('U', "..-");
        morseMap.put('V', "...-");
        morseMap.put('W', ".--");
        morseMap.put('X', "-..-");
        morseMap.put('Y', "-.--");
        morseMap.put('Z', "--..");
        morseMap.put('1', ".----");
        morseMap.put('2', "..---");
        morseMap.put('3', "...--");
        morseMap.put('4', "....-");
        morseMap.put('5', ".....");
        morseMap.put('6', "-....");
        morseMap.put('7', "--...");
        morseMap.put('8', "---..");
        morseMap.put('9', "----.");
        morseMap.put('0', "-----");

        for (HashMap.Entry<Character, String> entry : morseMap.entrySet()) {
            reverseMorseMap.put(entry.getValue(), entry.getKey());
        }
    }

    public String encode(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Tekst nie może być pusty");
        }

        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == ' ') {
                result.append("   ");
            } else {
                String morse = morseMap.get(c);
                if (morse == null) {
                    throw new IllegalArgumentException("Nieobsługiwany znak: " + c);
                }
                result.append(morse);
                if (i < text.length() - 1 && text.charAt(i + 1) != ' ') {
                    result.append(" ");
                }
            }
        }

        return result.toString();
    }

    public String decode(String morse) {
        if (morse == null || morse.isEmpty()) {
            throw new IllegalArgumentException("Kod Morse'a nie może być pusty");
        }

        StringBuilder result = new StringBuilder();
        String[] words = morse.split("   ");

        for (String word : words) {
            String[] letters = word.split(" ");
            for (String letter : letters) {
                if (!letter.isEmpty()) {
                    Character c = reverseMorseMap.get(letter);
                    if (c == null) {
                        throw new IllegalArgumentException("Nieprawidłowy kod Morse'a: " + letter);
                    }
                    result.append(c);
                }
            }
            result.append(" ");
        }

        return result.toString().trim();
    }
} 
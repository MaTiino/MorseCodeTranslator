package com.example.morsecodetranslator;

import com.google.firebase.Timestamp;

public class TranslationHistory {
    private String input;
    private String output;
    private Timestamp timestamp;
    private String userId;
    private boolean isEncoding;

    public TranslationHistory() {
        // Required empty constructor for Firestore
    }

    public TranslationHistory(String input, String output, boolean isEncoding, String userId) {
        this.input = input;
        this.output = output;
        this.isEncoding = isEncoding;
        this.userId = userId;
        this.timestamp = Timestamp.now();
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isEncoding() {
        return isEncoding;
    }

    public void setEncoding(boolean encoding) {
        isEncoding = encoding;
    }
} 
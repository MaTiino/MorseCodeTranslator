package com.example.morsecodetranslator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText inputField;
    private TextView outputField;
    private Button encodeButton, decodeButton, clearButton, playButton;
    private ImageButton menuButton;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private MediaPlayer dotSound, dashSound;
    private MorseCodeTranslator morseCodeTranslator;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        morseCodeTranslator = new MorseCodeTranslator();

        inputField = findViewById(R.id.inputField);
        outputField = findViewById(R.id.outputField);
        encodeButton = findViewById(R.id.encodeButton);
        decodeButton = findViewById(R.id.decodeButton);
        clearButton = findViewById(R.id.clearButton);
        playButton = findViewById(R.id.playButton);
        menuButton = findViewById(R.id.menuButton);

        dotSound = MediaPlayer.create(this, R.raw.dot);
        dashSound = MediaPlayer.create(this, R.raw.dash);

        encodeButton.setOnClickListener(v -> encodeText());
        decodeButton.setOnClickListener(v -> decodeText());
        clearButton.setOnClickListener(v -> clearFields());
        playButton.setOnClickListener(v -> playMorse());
        menuButton.setOnClickListener(v -> showMenu());
    }

    private void encodeText() {
        String input = inputField.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(this, "Wprowadź tekst do zakodowania", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String encoded = morseCodeTranslator.encode(input);
            outputField.setText(encoded);
            playButton.setEnabled(true);
            playButton.setBackgroundTintList(getColorStateList(R.color.purple_500));
            
            // Save to history
            TranslationHistory history = new TranslationHistory(input, encoded, true, FirebaseAuth.getInstance().getCurrentUser().getUid());
            saveToHistory(history);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            playButton.setEnabled(false);
            playButton.setBackgroundTintList(getColorStateList(R.color.purple_inactive));
        }
    }

    private void decodeText() {
        String input = inputField.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(this, "Wprowadź kod Morse'a do zdekodowania", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String decoded = morseCodeTranslator.decode(input);
            outputField.setText(decoded);
            playButton.setEnabled(false);
            playButton.setBackgroundTintList(getColorStateList(R.color.purple_inactive));
            
            // Save to history
            TranslationHistory history = new TranslationHistory(input, decoded, false, FirebaseAuth.getInstance().getCurrentUser().getUid());
            saveToHistory(history);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        inputField.setText("");
        outputField.setText("Tu pojawi się wynik");
        playButton.setEnabled(false);
        playButton.setBackgroundTintList(getColorStateList(R.color.purple_inactive));
    }

    private void playMorse() {
        String morse = outputField.getText().toString();
        if (morse.isEmpty() || morse.equals("Tu pojawi się wynik")) {
            Toast.makeText(this, "Najpierw zakoduj tekst", Toast.LENGTH_SHORT).show();
            return;
        }

        char[] morseChars = morse.toCharArray();
        playSequence(morseChars, 0);
    }

    private void playSequence(char[] morseChars, int index) {
        if (index >= morseChars.length) {
            return;
        }

        char currentChar = morseChars[index];
        MediaPlayer soundToPlay = null;

        if (currentChar == '.') {
            soundToPlay = dotSound;
        } else if (currentChar == '-') {
            soundToPlay = dashSound;
        }

        if (soundToPlay != null) {
            soundToPlay.setOnCompletionListener(mp -> {
                handler.postDelayed(() -> playSequence(morseChars, index + 1), 200);
            });
            soundToPlay.start();
        } else {
            handler.postDelayed(() -> playSequence(morseChars, index + 1), 400);
        }
    }

    private void showMenu() {
        PopupMenu popup = new PopupMenu(this, menuButton);
        popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_history) {
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
            } else if (id == R.id.menu_logout) {
                AuthUI.getInstance().signOut(this)
                        .addOnCompleteListener(task -> {
                            Intent intent = new Intent(this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        });
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void saveToHistory(TranslationHistory history) {
        FirebaseFirestore.getInstance()
                .collection("translations")
                .add(history)
                .addOnSuccessListener(documentReference -> {
                    Log.d("MainActivity", "Translation saved to history");
                })
                .addOnFailureListener(e -> {
                    Log.e("MainActivity", "Error saving translation to history", e);
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dotSound != null) {
            dotSound.release();
            dotSound = null;
        }
        if (dashSound != null) {
            dashSound.release();
            dashSound = null;
        }
        handler.removeCallbacksAndMessages(null);
    }
}

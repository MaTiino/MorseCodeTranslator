package com.example.morsecodetranslator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = "HistoryActivity";
    private RecyclerView historyRecyclerView;
    private HistoryAdapter adapter;
    private Button playButton;
    private ImageButton menuButton;
    private MediaPlayer dotSound, dashSound;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private String selectedMorseCode;
    private List<TranslationHistory> historyList;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        historyList = new ArrayList<>();
        adapter = new HistoryAdapter(this, historyList);

        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        playButton = findViewById(R.id.playButton);
        menuButton = findViewById(R.id.menuButton);

        dotSound = MediaPlayer.create(this, R.raw.dot);
        dashSound = MediaPlayer.create(this, R.raw.dash);

        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((position) -> {
            TranslationHistory item = historyList.get(position);
            Log.d(TAG, "Item clicked - isEncoding: " + item.isEncoding());
            Log.d(TAG, "Input: " + item.getInput());
            Log.d(TAG, "Output: " + item.getOutput());

            adapter.setSelectedPosition(position);
            if (item.isEncoding()) {
                selectedMorseCode = item.getOutput();
                playButton.setEnabled(true);
                playButton.setBackgroundTintList(getColorStateList(R.color.purple_500));
                Log.d(TAG, "Play button enabled for Morse code: " + selectedMorseCode);
            } else {
                selectedMorseCode = null;
                playButton.setEnabled(false);
                playButton.setBackgroundTintList(getColorStateList(R.color.purple_inactive));
                Log.d(TAG, "Play button disabled for decoded text");
            }
        });

        playButton.setOnClickListener(v -> playMorse());
        menuButton.setOnClickListener(v -> showMenu());

        loadHistory();
    }

    private void loadHistory() {
        String userId = auth.getCurrentUser().getUid();
        Log.d(TAG, "Loading history for user: " + userId);

        db.collection("translations")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    historyList.clear();
                    Log.d(TAG, "Successfully got documents. Count: " + queryDocumentSnapshots.size());
                    
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Log.d(TAG, "Document data: " + document.getData());
                        TranslationHistory history = document.toObject(TranslationHistory.class);
                        Log.d(TAG, "Converted to object - isEncoding: " + history.isEncoding());
                        historyList.add(history);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error loading history", e);
                    Toast.makeText(this, "Błąd ładowania historii: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void playMorse() {
        if (selectedMorseCode == null) return;

        playButton.setEnabled(false);
        int delay = 0;
        for (char c : selectedMorseCode.toCharArray()) {
            if (c == '.') {
                handler.postDelayed(() -> {
                    dotSound.start();
                    dotSound.setOnCompletionListener(mp -> mp.seekTo(0));
                }, delay);
                delay += 300;
            } else if (c == '-') {
                handler.postDelayed(() -> {
                    dashSound.start();
                    dashSound.setOnCompletionListener(mp -> mp.seekTo(0));
                }, delay);
                delay += 500;
            } else if (c == ' ') {
                delay += 300;
            }
        }
        handler.postDelayed(() -> playButton.setEnabled(true), delay);
    }

    private void showMenu() {
        PopupMenu popup = new PopupMenu(this, menuButton);
        popup.getMenuInflater().inflate(R.menu.history_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this::onMenuItemClick);
        popup.show();
    }

    private boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_back) {
            finish();
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
    }
} 
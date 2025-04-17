package com.example.pertemuan4.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pertemuan4.R;
import com.example.pertemuan4.util.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.Locale;

public class DetailCeritaActivity extends AppCompatActivity {
    private ImageView coverImage;
    private TextView titleText, storyText;
    private FloatingActionButton playButton;
    private TextToSpeech textToSpeech;
    private MediaPlayer mediaPlayer;
    private String story, audioUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cerita);
        coverImage = findViewById(R.id.cover);
        titleText = findViewById(R.id.tv_title);
        storyText = findViewById(R.id.story_text);
        playButton = findViewById(R.id.floatingActionButton);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Constans.EXTRA_TITLE);
        story = intent.getStringExtra(Constans.EXTRA_STORY_TEXT);
        audioUrl = intent.getStringExtra(Constans.EXTRA_AUDIO);
        String imageUrl = intent.getStringExtra(Constans.EXTRA_IMAGE);
        // Set teks dan gambar
        titleText.setText(title);
        storyText.setText(story);

        // Load gambar menggunakan Glide
        Glide.with(this).load(imageUrl).into(coverImage);

        // Tombol Play untuk memutar suara
        playButton.setOnClickListener(v -> {
            if (audioUrl.equals("GT")) {
                playTextToSpeech();
            } else {
                playAudio(audioUrl);
            }
        });
    }
    // Fungsi untuk memutar audio dengan MediaPlayer
    private void playAudio(String url) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        try {
            if (url.startsWith("http") || url.startsWith("https")) {
                mediaPlayer.setDataSource(url); // Jika audio dari URL
            } else {
                mediaPlayer.setDataSource(getAssets().openFd(url).getFileDescriptor());
            }
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(this, "Memutar Audio...", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Gagal Memuat Audio", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Fungsi untuk memutar Text-to-Speech
    private void playTextToSpeech() {
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.getDefault());
                textToSpeech.speak(story, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
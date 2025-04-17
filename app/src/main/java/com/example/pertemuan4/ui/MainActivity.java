package com.example.pertemuan4.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.pertemuan4.BuildConfig;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.pertemuan4.R;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CardView startTelling = findViewById(R.id.start_telling);
        CardView shareApp = findViewById(R.id.shareapp);
        CardView review = findViewById(R.id.review);

        startTelling.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ListCeritaActivity.class));
        });

        shareApp.setOnClickListener(v -> {
            String shareLink = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Ayo coba aplikasi e-book cerita anak! " + shareLink);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

        review.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
        });
    }
}
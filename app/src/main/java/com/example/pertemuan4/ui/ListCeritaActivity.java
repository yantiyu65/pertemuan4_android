package com.example.pertemuan4.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.pertemuan4.R;
import com.example.pertemuan4.adapter.ListStoryAdapter;
import com.example.pertemuan4.model.Story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListCeritaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListStoryAdapter adapter;
    private List<Story> storyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cerita);
        recyclerView = findViewById(R.id.storyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        storyList = loadStoriesFromJson();
        adapter = new ListStoryAdapter(storyList, this);
        recyclerView.setAdapter(adapter);
    }

    // Membaca JSON dari folder assets
    private List<Story> loadStoriesFromJson() {
        List<Story> stories = new ArrayList<>();
        try {
            InputStream inputStream = getAssets().open("data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("Data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString("title");
                String storyText = obj.getString("story_text");
                String audio = obj.getString("audio");
                String image = obj.getString("image");

                stories.add(new Story(title, storyText, audio, image));
            }
        } catch (IOException | JSONException e) {
            Log.e("JSON_ERROR", "Error parsing JSON", e);
        }
        return stories;
    }
}
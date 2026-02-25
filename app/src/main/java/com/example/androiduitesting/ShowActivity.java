package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_NAME = "extra_city_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView cityNameText = findViewById(R.id.cityNameText);
        Button backButton = findViewById(R.id.backButton2);

        String city = getIntent().getStringExtra(EXTRA_CITY_NAME);
        if (city == null) city = "";
        cityNameText.setText(city);

        backButton.setOnClickListener(v -> finish());
    }
}
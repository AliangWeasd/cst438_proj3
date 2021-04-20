package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SearchPartsActivity extends AppCompatActivity {

    private Button cpuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_parts);

        cpuButton = findViewById(R.id.cpuButton);

        cpuButton.setOnClickListener(v -> {
            Intent intent = ViewPartActivity.intentFactory(getApplicationContext(), "2");
            startActivity(intent);
        });
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, SearchPartsActivity.class);
    }
}
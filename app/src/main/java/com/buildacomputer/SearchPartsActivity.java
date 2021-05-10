package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SearchPartsActivity extends AppCompatActivity {

    private Button caseButton;
    private Button moboButton;
    private Button cpuButton;
    private Button gpuButton;
    private Button storageButton;
    private Button memoryButton;
    private Button coolButton;
    private Button powerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_parts);

        caseButton = findViewById(R.id.caseButton);
        caseButton.setOnClickListener(v -> {
            Intent intent = PartsRecyclerActivity.intentFactory(getApplicationContext(), 0);
            startActivity(intent);
        });

        moboButton = findViewById(R.id.moboButton);
        moboButton.setOnClickListener(v -> {
            Intent intent = PartsRecyclerActivity.intentFactory(getApplicationContext(), 1);
            startActivity(intent);
        });

        cpuButton = findViewById(R.id.cpuButton);
        cpuButton.setOnClickListener(v -> {
            Intent intent = PartsRecyclerActivity.intentFactory(getApplicationContext(), 2);
            startActivity(intent);
        });

        gpuButton = findViewById(R.id.gpuButton);
        gpuButton.setOnClickListener(v -> {
            Intent intent = PartsRecyclerActivity.intentFactory(getApplicationContext(), 3);
            startActivity(intent);
        });

        storageButton = findViewById(R.id.storageButton);
        storageButton.setOnClickListener(v -> {
            Intent intent = PartsRecyclerActivity.intentFactory(getApplicationContext(), 4);
            startActivity(intent);
        });

        memoryButton = findViewById(R.id.ramButton);
        memoryButton.setOnClickListener(v -> {
            Intent intent = PartsRecyclerActivity.intentFactory(getApplicationContext(), 5);
            startActivity(intent);
        });

        coolButton = findViewById(R.id.coolingButton);
        coolButton.setOnClickListener(v -> {
            Intent intent = PartsRecyclerActivity.intentFactory(getApplicationContext(), 6);
            startActivity(intent);
        });

        powerButton = findViewById(R.id.psuButton);
        powerButton.setOnClickListener(v -> {
            Intent intent = PartsRecyclerActivity.intentFactory(getApplicationContext(), 7);
            startActivity(intent);
        });
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, SearchPartsActivity.class);
    }
}
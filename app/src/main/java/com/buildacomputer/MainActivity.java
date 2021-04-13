package com.buildacomputer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private Button newBuildButton;
    private Button viewBuildsButton;
    private Button logoutButton;
    private Button deleteAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireupButtons();
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public void wireupButtons() {
        searchButton = findViewById(R.id.searchPartsButton);
        newBuildButton = findViewById(R.id.newBuildButton);
        viewBuildsButton = findViewById(R.id.viewBuildsButton);
        logoutButton = findViewById(R.id.logoutButton);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);

        searchButton.setOnClickListener(v -> {
            Intent intent = SearchPartsActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });
    }
}
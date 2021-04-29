package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMain extends AppCompatActivity {

    private Button partsButton;
    private Button usersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        wireupButtons();
    }

    private void wireupButtons() {

        usersButton = findViewById(R.id.usersButton);
        partsButton = findViewById(R.id.partsButton);

        usersButton.setOnClickListener(v -> {
            Intent intent = AdminViewUsers.intentFactory(getApplicationContext());
            startActivity(intent);
        });

        partsButton.setOnClickListener(v -> {
            Intent intent = AdminSearchParts.intentFactory(getApplicationContext());
            startActivity(intent);
        });

    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, AdminMain.class);
    }
}

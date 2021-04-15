package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button signUpButton;
    private Button guestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireupButtons();
    }

    public void wireupButtons() {
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        guestButton = findViewById(R.id.guestButton);

        loginButton.setOnClickListener(v -> {
            Intent intent = MainActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });

        signUpButton.setOnClickListener(v -> {
            Intent intent = SignupActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });

        guestButton.setOnClickListener(v -> {
            Intent intent = MainActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });
    }

    public static Intent intentFactory(Context context){
        return new Intent(context,LoginActivity.class);
    }
}
package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewPartActivity extends AppCompatActivity {

    private static final String MAIN = "com.example.intents.main";
    private TextView partType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_part);

        partType = findViewById((R.id.Part_Type));

        String text = getIntent().getStringExtra(MAIN);
        partType.setText(text);
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, ViewPartActivity.class);
    }

    public static Intent intentFactory(Context packageContext, String text) {
        Intent intent = new Intent(packageContext, ViewPartActivity.class);
        intent.putExtra(MAIN,text);
        return intent;
    }
}
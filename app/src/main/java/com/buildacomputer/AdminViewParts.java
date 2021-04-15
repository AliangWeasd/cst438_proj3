package com.buildacomputer;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class AdminViewParts extends AppCompatActivity {

    public static Intent intentFactory(Context context) {
        return new Intent(context, AdminViewParts.class);
    }
}

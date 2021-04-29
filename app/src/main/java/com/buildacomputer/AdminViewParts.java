package com.buildacomputer;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.buildacomputer.FirebaseAdapters.CompUsers;

import java.util.List;

public class AdminViewParts extends AppCompatActivity {

    RecyclerView recyclerView;


    public static Intent intentFactory(Context context) {
        return new Intent(context, AdminViewParts.class);
    }
}

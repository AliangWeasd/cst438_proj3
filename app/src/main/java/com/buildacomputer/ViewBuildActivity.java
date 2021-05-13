package com.buildacomputer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewBuildActivity extends AppCompatActivity {
    ArrayList<String> builds;
    RecyclerView recyclerView;
    HashMap build;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_build);
        recyclerView = findViewById(R.id.all_builds);
        getRecView();
    }

    private void getRecView() {
        Context context = this;
        String email = getIntent().getStringExtra("email");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Builds");
        Query checkBuild = ref.orderByChild("email").equalTo(email);
        checkBuild.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    build = (HashMap) data.getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static Intent intentFactory(Context context){
        return new Intent(context,ViewBuildActivity.class);
    }
}
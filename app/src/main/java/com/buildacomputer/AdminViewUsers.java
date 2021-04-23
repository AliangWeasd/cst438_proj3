package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buildacomputer.FirebaseAdapters.CompUsers;
import com.buildacomputer.RecyclerView.AdminUserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminViewUsers extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> username = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    Button delete;

    DatabaseReference database;
    HashMap user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_users);
        recyclerView = findViewById(R.id.user_list);
        Adapter adapter;
        getDatabase();
        //getRecView();


    }

    private void getDatabase() {
        database = FirebaseDatabase
                .getInstance()
                .getReference("compUser");
        Query dbUsers = database.orderByKey();
        dbUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.getChildren()){
                    user = (HashMap) i.getValue();
                    username.add((String) user.get("username"));
                    email.add((String) user.get("email"));
                }
                AdminUserAdapter adapter = new AdminUserAdapter(getApplicationContext(), username, email);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getRecView() {

        AdminUserAdapter adapter = new AdminUserAdapter(this, username, email);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, AdminViewUsers.class);
    }
}

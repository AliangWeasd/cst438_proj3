package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.buildacomputer.FirebaseAdapters.CompUsers;
import com.buildacomputer.RecyclerView.AdminUserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminEditActivity extends AppCompatActivity {
    EditText username;
    EditText firstName;
    EditText password;
    TextView email;
    Button editButton;
    Button deleteButton;
    String mUsername;
    CompUsers userProfile;

    DatabaseReference database;
    HashMap user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_user);
        pullUserFromIntent();
        getDatabase();
    }

    private void pullUserFromIntent() {
        mUsername = getIntent().getStringExtra("USERNAME");
    }

    private void getDatabase() {
        database = FirebaseDatabase
                .getInstance()
                .getReference("compUser");
        Query dbUser = database.orderByChild("username").equalTo(mUsername);
        dbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = (HashMap) snapshot.getChildren().iterator().next().getValue();
                firstName = findViewById(R.id.editName);
                username = findViewById(R.id.editUsername);
                password = findViewById(R.id.editPassword);
                email = findViewById(R.id.editEmail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void wireupDisplay() {
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, AdminEditActivity.class);
    }
}

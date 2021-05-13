package com.buildacomputer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.buildacomputer.FirebaseAdapters.CompUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private Button newBuildButton;
    private Button viewBuildsButton;
    private Button logoutButton;
    private Button deleteAccountButton;
    private Button adminButton;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    CompUsers userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserFromFB();

        wireupButtons();
    }

    private void getUserFromFB() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("compUser");
        userID = user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile = snapshot.getValue(CompUsers.class);
                if (userProfile!=null) {
                    Toast.makeText(MainActivity.this, userProfile.getUsername(), Toast.LENGTH_LONG).show();
                    deleteAccountButton.setVisibility(View.VISIBLE);
                    viewBuildsButton.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(MainActivity.this, "Guest Login", Toast.LENGTH_LONG).show();

                    deleteAccountButton.setVisibility(View.INVISIBLE);
                    viewBuildsButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Something went wrong.",Toast.LENGTH_LONG).show();

            }
        });
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
        adminButton = findViewById(R.id.adminButton);

        viewBuildsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ViewBuildActivity.intentFactory(MainActivity.this);
                intent.putExtra("email",userProfile.getEmail());
                startActivity(intent);

            }
        });


        searchButton.setOnClickListener(v -> {
            Intent intent = SearchPartsActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = LoginActivity.intentFactory(MainActivity.this);
                Toast.makeText(MainActivity.this,"Signed Out.",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminMain.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting your account will remove your builds from the system and cannot be undone.");
                dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Account Deleted",Toast.LENGTH_SHORT).show();
                                    Intent intent = LoginActivity.intentFactory(MainActivity.this);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(MainActivity.this,"Error please try again",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();

            }
        });
    }
}
package com.buildacomputer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.buildacomputer.FirebaseAdapters.CompUsers;
import com.buildacomputer.RecyclerView.AdminUserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AdminEditActivity extends AppCompatActivity {
    EditText username;
    EditText firstName;
    EditText password;
    TextView email;
    Button editButton;
    Button deleteButton;
    String mEmail;
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
        mEmail = getIntent().getStringExtra("EMAIL");
    }

    private void getDatabase() {
        database = FirebaseDatabase
                .getInstance()
                .getReference("compUser");
        Query dbUser = database.orderByChild("email").equalTo(mEmail);
        dbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = (HashMap) snapshot.getChildren().iterator().next().getValue();

                editButton = findViewById(R.id.editButton);
                deleteButton = findViewById(R.id.deleteButton);
                firstName = findViewById(R.id.editName);
                username = findViewById(R.id.editUsername);
                password = findViewById(R.id.editPassword);
                email = findViewById(R.id.editEmail);
                firstName.setText((String) user.get("name"));
                username.setText((String) user.get("username"));
                password.setText((String) user.get("password"));
                email.setText((String) user.get("email"));

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CompUsers update = new CompUsers(firstName.getText().toString(),
                                username.getText().toString(),
                                password.getText().toString(),
                                email.getText().toString());
                        FirebaseDatabase.getInstance().getReference("compUser")
                                .child(snapshot.getChildren().iterator().next().getKey())
                                .setValue(update);
                        Intent intent = AdminViewUsers.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(AdminEditActivity.this);
                        dialog.setTitle("Are you sure?");
                        dialog.setMessage("Deleting this account will remove the user from the system and cannot be undone.");
                        dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                snapshot.getChildren().iterator().next().getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(AdminEditActivity.this,"Account Deleted", Toast.LENGTH_SHORT).show();
                                            Intent intent = AdminViewUsers.intentFactory(getApplicationContext());
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(AdminEditActivity.this,"Error please try again",Toast.LENGTH_SHORT).show();
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

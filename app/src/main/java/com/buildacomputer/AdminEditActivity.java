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

                editButton = findViewById(R.id.editButton);
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
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(update);
//                        Map<String, Object> update = new HashMap<>();
//                        update.put("name", firstName.getText().toString());
//                        update.put("username", username.getText().toString());
//                        update.put("password", password.getText().toString());
//                        update.put("email", email.getText().toString());
//                        String ref = dbUser.getRef().toString();
//                        database.child(dbUser.getRef()).updateChildren(update);
                        Intent intent = AdminViewUsers.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }
                });

//                deleteButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        AlertDialog.Builder dialog = new AlertDialog.Builder();
//                        dialog.setTitle("Are you sure?");
//                        dialog.setMessage("Deleting your account will remove your builds from the system and cannot be undone.");
//                        dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()){
//                                            Toast.makeText(,"Account Deleted", Toast.LENGTH_SHORT).show();
//                                            Intent intent = LoginActivity.intentFactory();
//                                            startActivity(intent);
//                                        }else{
//                                            Toast.makeText(,"Error please try again",Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                            }
//                        });
//                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        });
//                        AlertDialog alert = dialog.create();
//                        alert.show();
//                    }
//                });
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

package com.buildacomputer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buildacomputer.FirebaseAdapters.CompUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private EditText name;
    private EditText username;
    private EditText password;
    private EditText email;
    private Button submit;
    private String uName;
    private String uUsername;
    private String uPassword;
    private String uEmail;
    private boolean valid = true;
    private HashMap user;
    private CompUsers user1;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();
        wireUpDisplay();

    }

    private void wireUpDisplay() {
        name = findViewById(R.id.editTextTextPersonName);
        username = findViewById(R.id.editTextTextUserName);
        password = findViewById(R.id.editTextTextPassword);
        email = findViewById(R.id.editTextTextEmail);
        submit = findViewById(R.id.signup_submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTheFields();
                if(checkTheFields()){
                    auth.createUserWithEmailAndPassword(uEmail,uPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        user1 = new CompUsers(uName,uUsername,uPassword,uEmail);
                                        FirebaseDatabase.getInstance().getReference("compUser")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(SignupActivity.this,"User Registered",Toast.LENGTH_SHORT).show();
                                                    Intent intent = LoginActivity.intentFactory(getApplicationContext());
                                                    startActivity(intent);
                                                }else {
                                                    Toast.makeText(SignupActivity.this,"User registration failed, try again.",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }else {
                                        Toast.makeText(SignupActivity.this,"User registration failed, try a different Email.",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                } }
        });
    }

    private void getTheFields() {
        uName = name.getText().toString();
        uUsername = username.getText().toString();
        uPassword = password.getText().toString();
        uEmail = email.getText().toString();
    }

    private boolean checkTheFields() {
        return (nameValid()&&usernameValid()&&validPass()&&validEmail());
    }

    private boolean validEmail() {
        if (TextUtils.isEmpty(uEmail)){
            email.setError("Email is required.");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()){
            email.setError("Please Enter valid email");
            return false;
        }
        email.setError(null);
        return true;

    }

    private boolean validPass() {
        if (TextUtils.isEmpty(uPassword)){
            password.setError("Password Cannot be Empty");
            return false;
        }
        if (uPassword.length()<6){
            password.setError("Password must be greater than 6 characters");
            return false;
        }
        password.setError(null);
        return true;
    }

    private boolean usernameValid() {

        if (TextUtils.isEmpty(uUsername)){
            username.setError("Username Cannot Be Empty");
            return false;
        }
        return true;

    }

    private boolean nameValid() {
        if (TextUtils.isEmpty(uName)){
            name.setError("Name Cannot be empty");
            return false;
        }
        name.setError(null);
        return true;

    }


    public static Intent intentFactory(Context context){
        return new Intent(context,SignupActivity.class);
    }
}
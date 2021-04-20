package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button signUpButton;
    private Button guestButton;
    private EditText email;
    private EditText password;
    private String uEmail;
    private String uPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();

        wireupButtons();
    }

    public void wireupButtons() {
        email = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        guestButton = findViewById(R.id.guestButton);

        loginButton.setOnClickListener(v -> {
            getTheFields();
            if (verifyFields()){
                mAuth.signInWithEmailAndPassword(uEmail,uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                            Intent intent = MainActivity.intentFactory(getApplicationContext());
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,"Login Failed. Check Credentials",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        signUpButton.setOnClickListener(v -> {
            Intent intent = SignupActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });

        guestButton.setOnClickListener(v -> {
            mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Logged in as Guest",Toast.LENGTH_SHORT).show();
                        Intent intent = MainActivity.intentFactory(LoginActivity.this);
                        startActivity(intent);
                    }
                }
            });
        });
    }

    private boolean verifyFields() {
        return (checkEmail()&&checkPassword());
    }

    private boolean checkPassword() {
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

    private boolean checkEmail() {
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

    private void getTheFields() {
        uEmail = email.getText().toString().trim();
        uPassword = password.getText().toString().trim();

    }

    public static Intent intentFactory(Context context){
        return new Intent(context,LoginActivity.class);
    }
}
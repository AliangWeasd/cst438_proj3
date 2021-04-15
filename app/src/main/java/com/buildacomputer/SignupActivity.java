package com.buildacomputer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buildacomputer.FirebaseAdapters.CompUsers;
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
    private Button submit;
    private String uName;
    private String uUsername;
    private String uPassword;
    private boolean valid = true;
    private HashMap user;
    private CompUsers user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        wireUpDisplay();

    }

    private void wireUpDisplay() {
        name = findViewById(R.id.editTextTextPersonName);
        username = findViewById(R.id.editTextTextUserName);
        password = findViewById(R.id.editTextTextPassword);
        submit = findViewById(R.id.signup_submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTheFields();
                if(checkTheFields()){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("compUser");
                    Query checkUser = ref.orderByChild("ID");
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            valid = true;
                            for (DataSnapshot data : snapshot.getChildren()){
                                user = (HashMap) data.getValue();
                                if (user.containsValue(uUsername)){
                                    Toast.makeText(getApplicationContext(),"Username already taken.",Toast.LENGTH_SHORT).show();
                                    username.setError("username taken");
                                    valid = false;
                                    break;
                                }
                            }
                            if (valid){
                                user1 = new CompUsers(uName,uUsername,uPassword,uUsername);
                                ref.child(user1.getId()).setValue(user1);
                                Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_SHORT).show();
                                Intent intent = MainActivity.intentFactory(getApplicationContext());
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                } }
        });
    }

    private void getTheFields() {
        uName = name.getText().toString();
        uUsername = username.getText().toString();
        uPassword = password.getText().toString();
    }

    private boolean checkTheFields() {
        return (nameValid()&&usernameValid()&&validPass());
    }

    private boolean validPass() {
        if (TextUtils.isEmpty(uPassword)){
            password.setError("Password Cannot be Empty");
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
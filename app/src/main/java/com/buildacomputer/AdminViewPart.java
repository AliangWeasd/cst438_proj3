package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.buildacomputer.FirebaseAdapters.CompParts;
import com.buildacomputer.FirebaseAdapters.CompUsers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class AdminViewPart extends AppCompatActivity {
    private static final String NAME = "com.example.intents.main";
    private static final int PART = 0;

    private Button editButton;
    private EditText partName;
    private ImageView imageView;
    private EditText description;
    private EditText etc;
    private EditText etc2;
    private EditText partURL;
    private String imageURL;

    private HashMap part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_part);

        editButton = findViewById(R.id.editItembutton);
        partName = findViewById(R.id.Part_Name);
        imageView = findViewById(R.id.Part_Picture);
        description = findViewById(R.id.Part_Description);
        etc = findViewById(R.id.Part_Etc);
        etc2 = findViewById(R.id.Part_Etc2);
        partURL = findViewById(R.id.Part_URL);

        String compPartName = getIntent().getStringExtra("NAME");
        int compPartType = getIntent().getIntExtra("PART",0);

        DatabaseReference refTypes = FirebaseDatabase.getInstance().getReference("CompPartType");
        Query checkTypes = refTypes.orderByChild("partType").equalTo(compPartType);
        checkTypes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    part = (HashMap) data.getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("compPart");
        Query checkPart = ref.orderByChild("name").equalTo(compPartName);
        checkPart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    part = (HashMap) data.getValue();
                    String id = part.get("id").toString();

                    partName.setText((String) part.get("name"));
                    description.setText((String) part.get("description"));
                    imageURL = (String) part.get("picture");
                    partURL.setText(imageURL);
                    Picasso.with(AdminViewPart.this).load(imageURL)
                            .resize(0, 500)
                            .into(imageView);

                    if (compPartType == 0) {
                        etc.setText(part.get("size").toString());
                    }
                    if (compPartType == 1) {
                        etc.setText(part.get("size").toString());
                        etc2.setText(part.get("memoryType").toString());
                    }
                    if (compPartType == 2) {
                        etc.setText(part.get("heatGen").toString());
                        etc2.setText(part.get("powerUse").toString());
                    }
                    if (compPartType == 3) {
                        etc.setText(part.get("heatGen").toString());
                        etc2.setText(part.get("powerUse").toString());
                    }
                    if (compPartType == 5) {
                        etc.setText(part.get("memoryType").toString());
                    }
                    if (compPartType == 6) {
                        etc.setText(part.get("heatCool").toString());
                        etc2.setText(part.get("powerUse").toString());
                    }
                    if (compPartType == 7) {
                        etc.setText(part.get("powerUse").toString());
                    }

                    editButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String newSize = "false";
                            String memoryType = "false";
                            String heatGen = "false";
                            String heatCool = "false";
                            String powerUse = "false";
                            String powerSupply = "false";

                            if (compPartType == 0) {
                                newSize = etc.getText().toString();
                            }
                            if (compPartType == 1) {
                                newSize = etc.getText().toString();
                                memoryType = etc2.getText().toString();
                            }
                            if (compPartType == 2) {
                                heatGen = etc.getText().toString();
                                heatCool = etc2.getText().toString();
                            }
                            if (compPartType == 3) {
                                heatGen = etc.getText().toString();
                                powerUse = etc2.getText().toString();
                            }
                            if (compPartType == 5) {
                                memoryType = etc.getText().toString();
                            }
                            if (compPartType == 6) {
                                heatCool = etc.getText().toString();
                                powerUse = etc2.getText().toString();
                            }
                            if (compPartType == 7) {
                                powerUse = etc.getText().toString();
                            }

                            CompParts update = new CompParts(partName.getText().toString(),
                                    description.getText().toString(),
                                    compPartType,
                                    partURL.getText().toString(),
                                    newSize, memoryType,
                                    heatGen, heatCool,
                                    powerUse, powerSupply);
                            ref.child(id).setValue(update);
                            Intent intent = AdminSearchParts.intentFactory(getApplicationContext());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, AdminViewPart.class);
        return intent;
    }
}

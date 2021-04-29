package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ViewPartActivity extends AppCompatActivity {

    private static final String NAME = "com.example.intents.main";
    private static final String PART = "com.example.intents.main";
    private TextView partName;
    private ImageView imageView;
    private TextView description;
    private TextView etc;
    private TextView title;
    private String imageURL;

    private HashMap part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_part);

        title = findViewById(R.id.searchTitle);
        partName = findViewById(R.id.Part_Name);
        imageView = findViewById(R.id.Part_Picture);
        description = findViewById(R.id.Part_Description);
        etc = findViewById(R.id.Part_Etc);
        Log.d("Hello","There");

        String compPartName = getIntent().getStringExtra("NAME");
        int compPartType = Integer.parseInt(getIntent().getStringExtra("PART"));

        DatabaseReference refTypes = FirebaseDatabase.getInstance().getReference("CompPartType");
        Query checkTypes = refTypes.orderByChild("partType").equalTo(compPartType);
        checkTypes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    part = (HashMap) data.getValue();
                    title.setText("Searching by: " + part.get("name"));
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

                    partName.setText((String) part.get("name"));
                    description.setText((String) part.get("description"));
                    imageURL = (String) part.get("picture");
                    Picasso.with(ViewPartActivity.this).load(imageURL)
                                .resize(0, 500)
                                .into(imageView);

                    String extraText = "";
                    if(!Objects.equals(part.get("size"), false)) {
                        extraText += "Size: " + part.get("size") + "\n";
                    }
                    if(!Objects.equals(part.get("memoryType"), false)) {
                        extraText += "Memory: " + part.get("memoryType") + "\n";
                    }
                    if(!Objects.equals(part.get("heatCool"), false)) {
                        extraText += "Cooling: " + part.get("heatCool") + " W\n";
                    }
                    if(!Objects.equals(part.get("heatGen"), false)) {
                        extraText += "TDP: " + part.get("heatGen") + " W\n";
                    }
                    if(!Objects.equals(part.get("powerUse"), false)) {
                        extraText += "Power Use: " + part.get("powerUse") + " W\n";
                    }
                    if(!Objects.equals(part.get("powerSupply"), false)) {
                        extraText += "Power Supply: " + part.get("powerSupply") + " W\n";
                    }

                    if(!extraText.isEmpty()) {
                        extraText = "Specifications: \n" + extraText;
                        etc.setText(extraText);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, ViewPartActivity.class);
        return intent;
    }
}
package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buildacomputer.FirebaseAdapters.CompUsers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.buildacomputer.FirebaseAdapters.CompParts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ViewPartActivity extends AppCompatActivity {

    private static final String MAIN = "com.example.intents.main";
    private TextView partName;
    private ImageView imageView;
    private TextView description;
    private TextView etc;
    private TextView title;
    private String imageURL;

    private CompParts part1;
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

        int compPartType = Integer.parseInt(getIntent().getStringExtra(MAIN));

        DatabaseReference refTypes = FirebaseDatabase.getInstance().getReference("CompPartType");
        Query checkTypes = refTypes.orderByChild("partType").equalTo(compPartType);
        checkTypes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    part = (HashMap) data.getValue();
                    title.setText("Searching by: " + (String)part.get("name"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("compPart");
        Query checkUser = ref.orderByChild("partType").equalTo(compPartType);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    if(!part.get("size").equals(false)) {
                        extraText += "Size: " + (Long) part.get("size") + "\n";
                    }
                    if(!part.get("memoryType").equals(false)) {
                        extraText += "Memory: " + (Long) part.get("memoryType") + "\n";
                    }
                    if(!part.get("heatCool").equals(false)) {
                        extraText += "Cooling: " + (Long) part.get("heatCool") + " W\n";
                    }
                    if(!part.get("heatGen").equals(false)) {
                        extraText += "TDP: " + (Long) part.get("heatGen") + " W\n";
                    }
                    if(!part.get("powerUse").equals(false)) {
                        extraText += "Power Use: " + (Long) part.get("powerUse") + " W\n";
                    }
                    if(!part.get("powerSupply").equals(false)) {
                        extraText += "Power Supply: " + (Long) part.get("powerSupply") + " W\n";
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

    public static Intent intentFactory(Context context) {
        return new Intent(context, ViewPartActivity.class);
    }

    public static Intent intentFactory(Context packageContext, String text) {
        Intent intent = new Intent(packageContext, ViewPartActivity.class);
        intent.putExtra(MAIN,text);
        return intent;
    }
}
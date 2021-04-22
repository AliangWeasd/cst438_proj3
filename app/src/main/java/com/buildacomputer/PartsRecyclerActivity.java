package com.buildacomputer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buildacomputer.FirebaseAdapters.CompParts;
import com.buildacomputer.RecyclerView.PartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartsRecyclerActivity extends AppCompatActivity {

    private static final String MAIN = "com.example.intents.main";

    RecyclerView recyclerView;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> partType= new ArrayList<>();
    ArrayList<String> picture= new ArrayList<>();

    List<CompParts> parts = new ArrayList<>();
    HashMap part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part_recycler);
        recyclerView = findViewById(R.id.part_list);

        getRecView();
    }

    protected void getRecView() {
        Context context = this;

        String partTypeText = getIntent().getStringExtra(MAIN);
        int compPartType = Integer.parseInt(partTypeText);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("compPart");
        Query checkUser = ref.orderByChild("partType").equalTo(compPartType);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    part = (HashMap) data.getValue();

                    CompParts party = new CompParts((String) part.get("name"), partTypeText, (String) part.get("picture"));
                    parts.add(party);
                }

                for (CompParts i : parts){
                    name.add(i.getName());
                    partType.add(partTypeText);
                    picture.add(i.getPicture());
                }

                PartAdapter adapter = new PartAdapter(context,name,partType,picture);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static Intent intentFactory(Context packageContext, String text) {
        Intent intent = new Intent(packageContext, PartsRecyclerActivity.class);
        intent.putExtra(MAIN,text);
        return intent;
    }
}
package com.buildacomputer;

// This page is used in the new build page.
// This page mirrors the PartsRecyclerActivity, presenting a list of parts pertaining to a part type.

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buildacomputer.FirebaseAdapters.CompParts;
import com.buildacomputer.RecyclerView.BuildPartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuildPartsRecyclerActivity extends AppCompatActivity {
    private static final int PART_TYPE = 0;

    RecyclerView recyclerView;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> id = new ArrayList<>();
    ArrayList<Integer> partType= new ArrayList<>();
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

        int partTypeExtra = getIntent().getIntExtra("PART_TYPE",0);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("compPart");
        Query checkUser = ref.orderByChild("partType").equalTo(partTypeExtra);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    part = (HashMap) data.getValue();

                    CompParts party = new CompParts((String) part.get("name"), Math.toIntExact((Long)part.get("id")), partTypeExtra, (String) part.get("picture"));
                    parts.add(party);
                }

                for (CompParts i : parts){
                    name.add(i.getName());
                    id.add(i.getId());
                    partType.add(partTypeExtra);
                    picture.add(i.getPicture());
                }

                BuildPartAdapter adapter = new BuildPartAdapter(context,name,id,partType,picture);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static Intent intentFactory(Context packageContext) {
        Intent intent = new Intent(packageContext, BuildPartsRecyclerActivity.class);
        return intent;
    }
}
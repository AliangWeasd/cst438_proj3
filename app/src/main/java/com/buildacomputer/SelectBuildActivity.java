package com.buildacomputer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.buildacomputer.RecyclerView.ViewBuildAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SelectBuildActivity extends AppCompatActivity {
    TextView bCase,bCooling,bCpu,bGpu,bMemory,bMobo,bPsu,bStorage;
    String buildName,email;
    HashMap finalBuild;
    HashMap part;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_build);
        wireUpDisplay();

    }

    private void wireUpDisplay() {
        bCase = findViewById(R.id.select_case);
        bCooling = findViewById(R.id.select_cooling);
        bCpu = findViewById(R.id.select_cpu);
        bGpu = findViewById(R.id.select_gpu);
        bMemory = findViewById(R.id.select_memory);
        bMobo = findViewById(R.id.select_mobo);
        bPsu = findViewById(R.id.select_psu);
        bStorage = findViewById(R.id.select_storage);

        buildName = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        String email = getIntent().getStringExtra("email");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Builds");
        Query checkBuild = ref.orderByChild("email").equalTo(email);
        checkBuild.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    HashMap build = (HashMap) data.getValue();
                    if (build.get("buildName").toString().equals(buildName)){
                        finalBuild = build;
                    }

                }
                fillTheFields();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void fillTheFields(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("compPart");
        Query getCase = reference.orderByChild("id").equalTo((long)finalBuild.get("caseID"));
        getCase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    part = (HashMap) data.getValue();
                }
                bCase.setText((String) part.get("name"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query getCooling = reference.orderByChild("id").equalTo((long)finalBuild.get("coolingID"));
        getCooling.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    part = (HashMap) data.getValue();
                }
                bCooling.setText((String) part.get("name"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query getCpu = reference.orderByChild("id").equalTo((long)finalBuild.get("cpuID"));
        getCpu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    part = (HashMap) data.getValue();
                }
                bCpu.setText((String) part.get("name"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query getGpu = reference.orderByChild("id").equalTo((long)finalBuild.get("gpuID"));
        getGpu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    part = (HashMap) data.getValue();
                }
                bGpu.setText((String) part.get("name"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query getMem = reference.orderByChild("id").equalTo((long)finalBuild.get("memoryID"));
        getMem.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    part = (HashMap) data.getValue();
                }
                bMemory.setText((String) part.get("name"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query getMobo = reference.orderByChild("id").equalTo((long)finalBuild.get("moboID"));
        getMobo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    part = (HashMap) data.getValue();
                }
                bMobo.setText((String) part.get("name"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query getPsu = reference.orderByChild("id").equalTo((long)finalBuild.get("psuID"));
        getPsu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    part = (HashMap) data.getValue();
                }
                bPsu.setText((String) part.get("name"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query getStorage = reference.orderByChild("id").equalTo((long)finalBuild.get("storageID"));
        getStorage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    part = (HashMap) data.getValue();
                }
                bStorage.setText((String) part.get("name"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, SelectBuildActivity.class);
    }
}
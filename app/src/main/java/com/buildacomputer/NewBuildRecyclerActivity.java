package com.buildacomputer;

// This page is used in the new build page.
// This page presents all eight part types and build name necessary to construct a build.
// Once the information is filled in and validated, the build is sent to the Firebase database.

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buildacomputer.FirebaseAdapters.CompParts;
import com.buildacomputer.RecyclerView.NewPartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NewBuildRecyclerActivity extends AppCompatActivity {
    // Information necessary to create a build.
    private static final String NAME = "com.example.intents.main";
    private static final String PICTURE = "com.example.intents.main";
    private static final String USER_EMAIL = "com.example.intents.main";
    private static final int TYPE = 0;
    private static final int ID = 0;

    // The amount of part types that must be kept track of.
    private static final int AMOUNT = 8;

    HashMap part;
    RecyclerView recyclerView;

    // Used to construct the part rows in the recycler when set.
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> id = new ArrayList<>();
    ArrayList<String> needed = new ArrayList<>();
    ArrayList<Integer> partType= new ArrayList<>();
    ArrayList<String> picture= new ArrayList<>();

    private Button createButton;
    private Button cancelButton;

    // Used to check if the build can be set into the database.
    private int buildFinished = 0;
    private boolean buildValid = false;

    String[] requirements;  // A string of hints for any missing parts in the build.

    // Information that will stay while the user is selecting parts.
    public static CompParts[] buildParts = new CompParts[AMOUNT]; // Database and Recycler View.
    private static String user_email = "";  // Candidate key
    private static String buildName = "";   // Candidate key

    // This combination will automatically change buildName to whatever is in buildNameText.
    private EditText buildNameText;
    private TextWatcher textWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            buildName = String.valueOf(s);
        }
    };

    @RequiresApi(api = android.os.Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_build_recycler);
        recyclerView = findViewById(R.id.part_list);
        buildNameText = findViewById(R.id.buildName);
        requirements = getResources().getStringArray(R.array.build_requirements); // Setting requirements
        buildNameText.addTextChangedListener(textWatcher);

        int id = getIntent().getIntExtra("ID",0);
        // A "-1" means that the user came here from MainActivity,
        // Any other number means that the user selected a part with such an ID and is returning.
        if(id != -1) {
            int index = getIntent().getIntExtra("TYPE",0);
            String partName = getIntent().getStringExtra("NAME");
            String pictureURL = getIntent().getStringExtra("PICTURE");
            CompParts party = new CompParts(partName, id, index, pictureURL);
            buildParts[index] = party;
        }
        else {
            // Resetting any leftover values.
            buildParts = new CompParts[AMOUNT];
            buildName = "";
            user_email = getIntent().getStringExtra("USER_EMAIL");
        }

        buildNameText.setText(buildName);
        recyclerView = findViewById(R.id.part_list);
        getRecView();

        wireButtons();
    }

    protected void getRecView() {
        Context context = this;

        for (int i = 0; i < AMOUNT; i++){
            // This is a part.
            if(buildParts[i] != null) {
                name.add(buildParts[i].getName());
                id.add(buildParts[i].getId());
                needed.add(requirements[i]);
                partType.add(i);
                picture.add(buildParts[i].getPicture());
            }
            // The part is not yet set. An ID of -1 indicates this to the recycler adapter.
            else {
                name.add("none");
                id.add(-1);
                needed.add(requirements[i]);
                partType.add(i);
                picture.add("none");
            }
        }

        NewPartAdapter adapter = new NewPartAdapter(context,name,id,partType,picture,needed);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, NewBuildRecyclerActivity.class);
    }

    public void wireButtons() {
        createButton = findViewById(R.id.createBuildButton);
        createButton.setOnClickListener(v -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Builds");
            Query checkUser = ref.orderByChild("email").equalTo(user_email);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = android.os.Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    buildFinished = 0;
                    buildValid = true;
                    for (int i = 0; i < AMOUNT; i++){
                        if(buildParts[i] != null) {
                            buildFinished++;
                        }
                    }

                    String buildMessage = "Build created!";

                    if(buildName.isEmpty()) {
                        buildMessage = "Build name needed";
                        buildValid = false;
                    }

                    if(buildFinished != AMOUNT) {
                        buildMessage = (AMOUNT - buildFinished) + " part(s) missing";
                        buildValid = false;
                    }

                    for (DataSnapshot data : snapshot.getChildren()) {
                        part = (HashMap) data.getValue();

                        String databaseBuildName = (String)part.get("buildName");
                        if(databaseBuildName.equals(buildName)) {
                            buildMessage = "Build name already in use";
                            buildValid = false;
                        }

                    }

                    Toast.makeText(NewBuildRecyclerActivity.this, buildMessage, Toast.LENGTH_SHORT).show();

                    if(buildValid) {
                        String key = ref.push().getKey();

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("buildName", buildName);
                        result.put("email", user_email);
                        result.put("caseID", buildParts[0].getId());
                        result.put("moboID", buildParts[1].getId());
                        result.put("cpuID", buildParts[2].getId());
                        result.put("gpuID", buildParts[3].getId());
                        result.put("storageID", buildParts[4].getId());
                        result.put("memoryID", buildParts[5].getId());
                        result.put("coolingID", buildParts[6].getId());
                        result.put("psuID", buildParts[7].getId());

                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(key, result);
                        ref.updateChildren(childUpdates);

                        Intent intent = MainActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(NewBuildRecyclerActivity.this, "Build failed to save", Toast.LENGTH_SHORT).show();
                    Intent intent = MainActivity.intentFactory(getApplicationContext());
                    startActivity(intent);
                }
            });
        });

        cancelButton = findViewById(R.id.cancelBuildButton);
        cancelButton.setOnClickListener(v -> {
            Intent intent = MainActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });
    }
}

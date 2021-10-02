package com.CRM.opportunity.Action;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.CRM.opportunity.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

public class ActionsActivity extends AppCompatActivity {
    RecyclerView recycervew_actions;
    myadapterActions adapter_actions;
    Query query;
    DatabaseReference reference2;
    StorageReference fileRef;
    FirebaseUser user;
    String userId;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

        setTitle("           Last Actions");
        getSupportActionBar().setElevation(15);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        recycervew_actions=findViewById(R.id.recycervew_actions);
        recycervew_actions.setLayoutManager(new LinearLayoutManager(ActionsActivity.this));


//        reference2 = FirebaseDatabase.getInstance().getReference();
//        query = reference2.child("Actions");

        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId =test_user.getString("id_status","");

        if (checkId.equals("true") || checkId=="true"){
//            Toast.makeText(this, "you are is :"+checkId, Toast.LENGTH_SHORT).show();
            reference2 = FirebaseDatabase.getInstance().getReference();
            reference2.keepSynced(true);
            query = reference2.child("Actions");
            reference2.keepSynced(false);

        }else {
            reference2 = FirebaseDatabase.getInstance().getReference();
            reference2.keepSynced(true);
            query = reference2.child("Actions").orderByChild("userId").equalTo(userId);
            reference2.keepSynced(false);
        }


        FirebaseRecyclerOptions<ActionsClass> options =
                new FirebaseRecyclerOptions.Builder<ActionsClass>()
                        .setQuery(query, ActionsClass.class)
                        .build();

        adapter_actions = new myadapterActions(options);
        recycervew_actions.setAdapter(adapter_actions);




    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_actions.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter_actions.stopListening();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
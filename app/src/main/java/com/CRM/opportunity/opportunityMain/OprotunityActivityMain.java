package com.CRM.opportunity.opportunityMain;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.CRM.opportunity.R;
import com.CRM.opportunity.oppertunites.ListData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class OprotunityActivityMain extends AppCompatActivity {

   private RecyclerView rv;
    private List<ListData> listDatumOpps;
    String userId;
    FirebaseAuth fAuth;
    private myadapterOpp adapter;
    DatabaseReference reference2;
    Query query;
    DatabaseReference mDatabase;
//    private  MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oprotunity_main);
        rv=findViewById(R.id.opprentity_main_recycler_view);

        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        getSupportActionBar().setElevation(15);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Opertunites");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("oppertunite");
        mDatabase.keepSynced(true);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        listDatumOpps = new ArrayList<>();


        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId =test_user.getString("id_status","");

        if (checkId.equals("true") || checkId=="true"){
//            Toast.makeText(this, "you are is :"+checkId, Toast.LENGTH_SHORT).show();
            reference2 = FirebaseDatabase.getInstance().getReference();
            reference2.keepSynced(true);
            query = reference2.child("oppertunite");
            reference2.keepSynced(false);

        }else {
            reference2 = FirebaseDatabase.getInstance().getReference();
            reference2.keepSynced(true);
            query = reference2.child("oppertunite").orderByChild("userId").equalTo(userId);
            reference2.keepSynced(false);
        }

        FirebaseRecyclerOptions<Oprentity> options =
                new FirebaseRecyclerOptions.Builder<Oprentity>()
                        .setQuery(query, Oprentity.class)
                        .build();

        adapter = new myadapterOpp(options);
        rv.setAdapter(adapter);



    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }



    @Override
    protected void onPause() {
        super.onPause();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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
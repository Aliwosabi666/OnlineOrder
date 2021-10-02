package com.CRM.opportunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.CRM.opportunity.client.AddClient;
import com.CRM.opportunity.client.Client;
import com.CRM.opportunity.client.myadapter;
import com.CRM.opportunity.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ClientsMain extends AppCompatActivity {
    RecyclerView recview;
    myadapter adapter;
    FloatingActionButton fb;
    FirebaseUser user;
    String userId;
    FirebaseAuth fAuth;
    DatabaseReference CountRef;
    DatabaseReference reference2;
    Query query;
    TextView txtcount;
    private int countclt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_main);
        setTitle("Clients");



        getSupportActionBar().setElevation(15);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
/*
        CountRef = FirebaseDatabase.getInstance().getReference().child("Client");
*/

        recview = findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(ClientsMain.this));


       /* txtcount = (TextView) findViewById(R.id.Count_clt);*/






//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//         query = reference.child("Client").orderByChild("userId").equalTo(userId);

        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId =test_user.getString("id_status","");

        if (checkId.equals("true") || checkId=="true"){
//            Toast.makeText(this, "you are is :"+checkId, Toast.LENGTH_SHORT).show();
            reference2 = FirebaseDatabase.getInstance().getReference();
            reference2.keepSynced(true);
            query = reference2.child("Client");

        }else {
            reference2 = FirebaseDatabase.getInstance().getReference();
            reference2.keepSynced(true);
            query = reference2.child("Client").orderByChild("userId").equalTo(userId);
        }


        FirebaseRecyclerOptions<Client> options =
                new FirebaseRecyclerOptions.Builder<Client>()
                        .setQuery(query, Client.class)
                        .build();

        adapter = new myadapter(options);
        recview.setAdapter(adapter);

        fb = (FloatingActionButton) findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddClient.class));
            }
        });


/*        CountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                   // count_client = (int) snapshot.getChildrenCount();
                    countclt = (int) snapshot.getChildrenCount();
                    txtcount.setText(Integer.toString(countclt)+ " Client");
                  //  System.out.println("In onDataChange, count="+countclt);

                }
                else {
                    txtcount.setText("0 Client");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    Query query_search;
    private void processsearch(String s) {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);

        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId =test_user.getString("id_status","");


        if (checkId.equals("true") || checkId=="true"){

            FirebaseRecyclerOptions<Client> options =
                    new FirebaseRecyclerOptions.Builder<Client>()
                            .setQuery(reference.child("Client").orderByChild("nom").startAt(s).endAt(s + "\uf8ff"), Client.class)
                            .build();

            adapter = new myadapter(options);
            adapter.startListening();
            recview.setAdapter(adapter);

        }else {
            FirebaseRecyclerOptions<Client> options =
                    new FirebaseRecyclerOptions.Builder<Client>()
                            .setQuery(reference.child("Client").child(userId).orderByChild("nom").startAt(s).endAt(s + "\uf8ff"), Client.class)
                            .build();

            adapter = new myadapter(options);
            adapter.startListening();
            recview.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
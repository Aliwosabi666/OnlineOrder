package com.CRM.opportunity.oppertunites;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.CRM.opportunity.R;
import com.CRM.opportunity.oppertunites.Adapter.TabAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OpportunitiesStatus extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tab;
    private ViewPager viewPager;
    ArrayList arrayNameTabs;
    long numbersTabs;
    String userId;
    FirebaseAuth fAuth;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference demografiRef;
    DatabaseReference mDatabase;
//    int idStauteop=-1;


    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int REQUEST_TAKE_PHOTO = 101;
    public static final int REQUEST_IMAGE_UPLOAD = 2;
    private static final String TAG = "ProductsManipulation";

    private Button floatingActionButton2;
    private LinearLayout disble_add_status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.opportunities_status_activity);
        setTitle("Opportunities");
        getSupportActionBar().setElevation(15);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        floatingActionButton2=findViewById(R.id.floatingActionButton2);
        disble_add_status=findViewById(R.id.disble_add_status);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("oppertunite");
        mDatabase.keepSynced(true);

        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId =test_user.getString("id_status","");

        if (checkId.equals("false") || checkId=="false"){
           floatingActionButton2.setVisibility(View.GONE);
        }




        viewPager =  findViewById(R.id.viewPager);
        tab = findViewById(R.id.tabLayout);

        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

      arrayNameTabs = new ArrayList();
//        arrayNameTabs.add("First");
//        arrayNameTabs.add("Second");
//        arrayNameTabs.add("Thired");


        demografiRef = rootRef.child("oppertunitestate");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int idStauts=-1;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String nama = ds.child("name").getValue(String.class);
                    boolean check_id_status=ds.child("check_status").getValue(boolean.class);
                     idStauts = ds.child("id").getValue(Integer.class);
                     if (check_id_status){
                         if (idStauts >0){
                             tab.addTab(tab.newTab().setText("" + nama));
                         }
                     }
                }
                adapter = new TabAdapter
                        (getSupportFragmentManager(),idStauts);
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(1);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
//                for (int x=0; x<arrayNameTabs.size()-1;x++){
//                    tab.addTab(tab.newTab().setText("" + arrayNameTabs.get(x)));
//                }
//                Log.d("sizeArrays", "sizeArrays14: " + tab.getTabCount()+1);
//                numbersTabs= arrayNameTabs.size();

                Log.d("sizeArrays", "sizeArrays1: " + arrayNameTabs.size());

                Log.d("TAG", String.valueOf(dataSnapshot.getChildrenCount()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        demografiRef.addListenerForSingleValueEvent(valueEventListener);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }

    public void addNewTab(View view) {




        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New State");



// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                stateName = input.getText().toString();
                if (input.getText().toString().length()<=0){
                    input.setError("please insert data");
                    Toast.makeText(OpportunitiesStatus.this, "No Data insert", Toast.LENGTH_SHORT).show();
                    return;
                }
                getIdStauts(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();





//        getIdStauts();
//        Map<String, Object> map = new HashMap<>();
//        getIdStauts();
//        map.put("name", "kmal");
//        int idstat=getIdStauts();
//        Log.d("TAG", "idstatis1: "+getIdStauts());
//        if (getIdStauts() <= 0) {
//            Log.d("TAG", "idstatis2: "+getIdStauts());
//            map.put("id", 1);
//                }
//        else {
//            Log.d("TAG", "idstatis3: "+getIdStauts());
////            Log.d("TAG", "idstatis: "+idstat);
//            map.put("id", getIdStauts() + 1);
//                }
//                    currentData.setValue(1);
//                } else {
//                    currentData.setValue((Long) currentData.getValue() + 1);
//                }
//
//        map.put("id", 1);
//        FirebaseDatabase.getInstance().getReference().child("oppertunite").push()
//                .setValue(map)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        adapter.notifyDataSetChanged();
//                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
//                        finish();
//                        startActivity(new Intent(getApplicationContext(), OpportunitiesStatus.class));
//
//                    }
//
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//
//
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        adapter.notifyDataSetChanged();

//        for (int k = 0; k <arrayNameTabs.size(); k++) {
//            tab.addTab(tab.newTab().setText("" + arrayNameTabs.get(k)));
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    void getIdStauts(final String stat_name){

        demografiRef = rootRef.child("id_stat").child(userId);

        demografiRef.runTransaction(new Transaction.Handler() {
            int id;
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    mutableData.setValue(1);
                } else {
                    int count = mutableData.getValue(Integer.class);
                    mutableData.setValue(count + 1);
                }
                id=mutableData.getValue(Integer.class);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean success, DataSnapshot dataSnapshot) {
                // Analyse databaseError for any error during increment
                Map<String, Object> map = new HashMap<>();
                map.put("name", stat_name);
                map.put("id",id );
                map.put("user_id",userId );
                map.put("check_status",true );

                FirebaseDatabase.getInstance().getReference().child("oppertunitestate").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), OpportunitiesStatus.class));

                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();
                            }
                        });




            }
        });
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

package com.CRM.opportunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.CRM.opportunity.oppertunites.OpportunitiesStatus;
import com.CRM.opportunity.opportunityMain.OprotunityActivityMain;
import com.CRM.opportunity.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddOppertunite extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference demografiRef;
    EditText txtlib_opport, txtrevenu_espere, txtprobabilite;
    TextView txtdate_fermeture;
    ArrayList<String> list;
    String customer_name;
    DatePickerDialog datePickerDialog;
    String userId;
    FirebaseAuth fAuth;
    Query query;
    DatabaseReference reference2;
    SweetAlertDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_oppertunite);

        txtlib_opport = findViewById(R.id.txtlib_opport);
        txtrevenu_espere = findViewById(R.id.txtrevenu_espere);
        txtprobabilite = findViewById(R.id.txtprobabilite);
        txtdate_fermeture = findViewById(R.id.txtdate_fermeture);

        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        list = new ArrayList<String>();

        Spinner spinner;


        if (getIntent().getExtras() != null) {


            ArrayList nameCustoemr = new ArrayList();

            txtlib_opport.setText(getIntent().getStringExtra("opport"));
            txtrevenu_espere.setText(getIntent().getStringExtra("espere"));
            txtprobabilite.setText(getIntent().getStringExtra("txtprobabilite"));
            txtdate_fermeture.setText(getIntent().getStringExtra("fermeture"));
            String  customerName = getIntent().getStringExtra("name_customer");
//            nameCustoemr.add(customerName);

//            int id_status = Integer.parseInt(getIntent().getStringExtra("id_status"));
            String  id_oppertuntiy = getIntent().getStringExtra("id_oppertuntiy");
            String  userId = getIntent().getStringExtra("userId");
            String  postion = getIntent().getStringExtra("position");

            list.add(customerName);


        }else {
            list.add("Choose a client");
        }



        txtdate_fermeture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddOppertunite.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                txtdate_fermeture.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        final String checkId =test_user.getString("id_status","");





        demografiRef = rootRef.child("Client");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String nama = ds.child("nom").getValue(String.class);
                    String checktypeUser = ds.child("userId").getValue(String.class);

                    if (checkId.equals("true") || checkId=="true"){
                        Log.d("keyuser", "onDataChange: " + dataSnapshot.getKey());
                        list.add(nama);
//

                    }
                    if (checkId.equals("false") ){
                        if (userId.equals(checktypeUser)){
                            list.add(nama);
                        }
                    }



                }
                Log.d("TAG", String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        demografiRef.addListenerForSingleValueEvent(valueEventListener);

        spinner = (Spinner) findViewById(R.id.spiner_customers);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.spiner_custome_style, list);
        adp.setDropDownViewResource(R.layout.spiner_custome_style);
        spinner.setAdapter(adp);
        spinner.setOnItemSelectedListener(this);







    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        customer_name = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), customer_name, Toast.LENGTH_SHORT).show();
        Log.d("itemselected", "the item selected is  " + customer_name);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void processSave() {
        if (txtlib_opport.length() == 0) {
            txtlib_opport.setError("Plz enter your opport");
        } else if (txtrevenu_espere.length() == 0) {
            txtrevenu_espere.setError("Plz enter your espere");
        } else if (txtprobabilite.length() == 0) {
            txtprobabilite.setError("Plz enter your babilite");
        } else if (txtdate_fermeture.length() == 0) {
            txtdate_fermeture.setError("Plz enter your fermeture");
        }
//        else if (!spiner_customers.isSelected()){
//            spiner_customers.setError("Plz enter your coor gps");
//        }
        else {

             pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#0D5C78"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(true);
            pDialog.show();


            demografiRef = rootRef.child("id_oppertuntiy");
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
                    id = mutableData.getValue(Integer.class);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                    Map<String, Object> map = new HashMap<>();


                    if (getIntent().getExtras() != null) {

                        map.put("opport", txtlib_opport.getText().toString());
                        map.put("espere", txtrevenu_espere.getText().toString());
                        map.put("txtprobabilite", txtprobabilite.getText().toString());
                        map.put("fermeture", txtdate_fermeture.getText().toString());
                        map.put("name_customer", customer_name);
//                        map.put("id_status", 1);
//                        map.put("id_oppertuntiy", id);
                        map.put("userId", userId);


                        String  postion = getIntent().getStringExtra("position");

                        FirebaseDatabase.getInstance().getReference().child("oppertunite")
                                .child(postion).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pDialog.dismissWithAnimation();
                                        Toast.makeText(AddOppertunite.this, "Update Sucess", Toast.LENGTH_SHORT).show();


                                        startActivity(new Intent(getApplicationContext(), OprotunityActivityMain.class));
                                        finish();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pDialog.dismissWithAnimation();

                                    }
                                });



                    }else {

                        map.put("opport", txtlib_opport.getText().toString());
                        map.put("espere", txtrevenu_espere.getText().toString());
                        map.put("txtprobabilite", txtprobabilite.getText().toString());
                        map.put("fermeture", txtdate_fermeture.getText().toString());
                        map.put("name_customer", customer_name);
                        map.put("id_status", 1);
                        map.put("id_oppertuntiy", id);
                        map.put("userId", userId);


                        FirebaseDatabase.getInstance().getReference().child("oppertunite").push()
                                .setValue(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        txtlib_opport.setText("");
                                        txtrevenu_espere.setText("");
                                        txtprobabilite.setText("");
                                        txtdate_fermeture.setText("");

                                        pDialog.dismissWithAnimation();

                                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), OpportunitiesStatus.class));

                                    }

                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pDialog.dismissWithAnimation();
                                        Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();
                                    }
                                });





                    }






                }
            });


        }
    }




//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }

    public void back_op(View view) {
        onBackPressed();
    }



    void addIdOppertunity() {
        demografiRef = rootRef.child("id_oppertuntiy");

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
                id = mutableData.getValue(Integer.class);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }

    public void save_op(View view) {
        processSave();
    }
}
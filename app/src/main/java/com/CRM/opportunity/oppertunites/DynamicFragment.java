package com.CRM.opportunity.oppertunites;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.CRM.opportunity.AddOppertunite;
import com.CRM.opportunity.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DynamicFragment extends Fragment implements View.OnClickListener {
    View view;
    int val;
    TextView c;
    private List<ListData> listDatumOpps;
    private RecyclerView rv;
    private MyAdapter adapter, adapterSpiner;
    FloatingActionButton floatingActionButton_oppertunit;
    String userId;
    FirebaseAuth fAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.opportunity_new_layout, container, false);
        val = getArguments().getInt("someInt", 0);
        c = view.findViewById(R.id.c);

        floatingActionButton_oppertunit = view.findViewById(R.id.floatingActionButton_oppertunit);

        rv = (RecyclerView) view.findViewById(R.id.recview_opportunity);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        listDatumOpps = new ArrayList<>();

        floatingActionButton_oppertunit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddOppertunite.class));
                ((Activity)getContext()).finish();

            }
        });

        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();


        SharedPreferences test_user = getContext().getSharedPreferences("userInfo", 0);
        final String checkId = test_user.getString("id_status", "");

        final DatabaseReference demografiRef = FirebaseDatabase.getInstance().getReference().child("oppertunite");
        demografiRef.keepSynced(true);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {

                    int id = npsnapshot.child("id_status").getValue(Integer.class);
                    String userCheckId=npsnapshot.child("userId").getValue(String.class);
//                    boolean check_status=npsnapshot.child("userId").getValue(boolean.class);

//                    if (check_status){

                        if (val == id) {

                            if (checkId.equals("true") || checkId == "true") {

                                ListData l = npsnapshot.getValue(ListData.class);
                                listDatumOpps.add(l);

                            }else {
                                if (userId.equals(userCheckId)){
                                    ListData l = npsnapshot.getValue(ListData.class);
                                    listDatumOpps.add(l);
                                }
                            }

//                        }
                    }


                }

//                adapterSpiner=new MyAdapter(getContext(),getItems_Status());
                adapter = new MyAdapter(listDatumOpps, getItems_Status());

                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        demografiRef.addListenerForSingleValueEvent(valueEventListener);


        return view;
    }

    public static DynamicFragment addfrag(int val) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }


    void getDataFunction(View view) {
        DatabaseReference demografiRef;
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        demografiRef = rootRef.child("oppertunite");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String nama = ds.child("name").getValue(String.class);
                    int id = ds.child("id").getValue(Integer.class);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        demografiRef.addListenerForSingleValueEvent(valueEventListener);

    }

    @Override
    public void onClick(View view) {

    }

    ArrayList getItems_Status() {
        DatabaseReference demografiRef;
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.keepSynced(true);

        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        final ArrayList nameStatus = new ArrayList();

        demografiRef = rootRef.child("oppertunitestate").child(userId);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String nama = ds.child("name").getValue(String.class);
                    boolean check_status=ds.child("check_status").getValue(boolean.class);

                    if (check_status){
                        nameStatus.add(nama);
                    }

                }
                Log.d("TAG", String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        demografiRef.addListenerForSingleValueEvent(valueEventListener);
        return nameStatus;
    }

}
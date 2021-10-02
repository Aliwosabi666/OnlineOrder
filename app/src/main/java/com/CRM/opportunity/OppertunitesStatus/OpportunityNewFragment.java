package com.CRM.opportunity.OppertunitesStatus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.CRM.opportunity.R;

public class OpportunityNewFragment extends Fragment implements View.OnClickListener {
//    private List<ListData> listData;
//    private RecyclerView rv;
//    private MyAdapter adapter;
//    FloatingActionButton floatingActionButton_oppertunit;
//    String userId;
//    FirebaseAuth fAuth;
//
//    @Nullable
//    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.opportunity_new_layout, container, false);
//        floatingActionButton_oppertunit = view.findViewById(R.id.floatingActionButton_oppertunit);
//        rv = (RecyclerView) view.findViewById(R.id.recview_opportunity);
//        rv.setHasFixedSize(true);
//        rv.setLayoutManager(new LinearLayoutManager(getContext()));
//        listData = new ArrayList<>();
//
//        floatingActionButton_oppertunit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), AddOppertunite.class));
//            }
//        });
//
//        fAuth = FirebaseAuth.getInstance();
//        userId = fAuth.getCurrentUser().getUid();
//
//        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference().child("oppertunite").child(userId);
//        nm.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
//                        ListData l = npsnapshot.getValue(ListData.class);
//                        listData.add(l);
//                    }
//                    adapter = new MyAdapter(listData);
//                    rv.setAdapter(adapter);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//
//
        return view;

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

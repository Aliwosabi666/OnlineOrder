package com.CRM.opportunity.oppertunites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.CRM.opportunity.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.dialogplus.DialogPlus;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements AdapterView.OnItemSelectedListener   {
    private List<ListData> listDatumOpps;
    String userId;
    FirebaseAuth fAuth;
    Context mContext;
    int item_status;
    ArrayList<String> nameStatus;
    ArrayList<String> itemstatusfromfragments;
    ArrayAdapter<String> adapter_spiner;
    FirebaseFirestore db ;
    String status_name_selected;


    public MyAdapter(List<ListData> listDatumOpps, ArrayList itemstatusfromfragments) {
        this.listDatumOpps = listDatumOpps;
        this.itemstatusfromfragments = itemstatusfromfragments;
    }

    public MyAdapter(List<ListData> listDatumOpps) {
        this.listDatumOpps = listDatumOpps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opportunity_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ListData ld = listDatumOpps.get(position);

        Calendar calendar = Calendar.getInstance();
        final String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        db= FirebaseFirestore.getInstance();

        holder.txtid.setText(ld.getOpport());
        holder.txtname.setText(ld.getEspere());
        holder.proprey.setText(ld.getTxtprobabilite());
        holder.date_op.setText(ld.fermeture);
        holder.name_client.setText(ld.getName_customer());

        SharedPreferences test_user = mContext.getSharedPreferences("userInfo", 0);
        String checkId =test_user.getString("id_status","");

        if (checkId.equals("true") || checkId=="true"){

            db.collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    if (ld.getUserId().equals(document.getId())){

                                        String name = String.valueOf(document.get("fName"));
                                        holder.user_name_op.setText(name);
                                    }

                                }
                            } else {
                                Log.w("TAG", "Error getting documents.", task.getException());
                            }


                        }

                    });
        }else {
            holder.user_name_op.setVisibility(View.GONE);
        }










        holder.editStatus.setOnClickListener(new View.OnClickListener() {
            ArrayList<String> list = new ArrayList<String>();

            @Override
            public void onClick(final View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.editStatus.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialogstatusitem))
                        .setExpanded(true, 1200)
                        .create();

                View myview = dialogPlus.getHolderView();
                final Spinner spiner_status_edit = myview.findViewById(R.id.spiner_status_edit);
                final  LinearLayout back_button =myview.findViewById(R.id.back_button);

                back_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogPlus.dismiss();

//                        mContext.startActivity(new Intent(mContext, OpportunitiesStatus.class));
                    }
                });

                final DatabaseReference demografiRef;
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                fAuth = FirebaseAuth.getInstance();
                userId = fAuth.getCurrentUser().getUid();

                demografiRef = rootRef.child("oppertunitestate");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String nama = ds.child("name").getValue(String.class);
                            int id_status = ds.child("id").getValue(Integer.class);
                            Log.d("keyuser", "onDataChange: " + dataSnapshot.getKey() + "the refrenc is" + demografiRef);
                            list.add(nama);
                            Log.d("TAGStatus", nama);
                        }


                        adapter_spiner = new ArrayAdapter<String>(view.getContext(), R.layout.spiner_custome_style, list);
                        adapter_spiner.setDropDownViewResource(R.layout.spiner_custome_style);
                        spiner_status_edit.setAdapter(adapter_spiner);
                        adapter_spiner.notifyDataSetChanged();

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                };



                list.clear();



                demografiRef.addListenerForSingleValueEvent(valueEventListener);

                spiner_status_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        int itemSelected = i;
                        item_status = i;
                        status_name_selected= String.valueOf(adapterView.getSelectedItem());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                Button save_edit_stat = myview.findViewById(R.id.edit_stat);
                save_edit_stat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        final DatabaseReference demografiRef = FirebaseDatabase.getInstance().getReference().child("oppertunite");
                        ValueEventListener valueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                                    String name = npsnapshot.child("name").getValue(String.class);
                                    int id = npsnapshot.child("id_status").getValue(Integer.class);
                                    int id_oppertuntiy = npsnapshot.child("id_oppertuntiy").getValue(Integer.class);
                                    Map<String, Object> updates = new HashMap<String, Object>();
                                    if (ld.getId_oppertuntiy() == id_oppertuntiy) {
                                        int x = item_status;
                                        int y = +x + 1;
                                        updates.put("opport", ld.getOpport());
                                        updates.put("espere", ld.getEspere());
                                        updates.put("txtprobabilite", ld.getTxtprobabilite());
                                        updates.put("fermeture", ld.getFermeture());
                                        updates.put("name_customer", ld.getName_customer());
                                        updates.put("id_status", y);
                                        updates.put("id_oppertuntiy", ld.getId_oppertuntiy());
                                        updates.put("userId", userId);

                                        npsnapshot.getRef().setValue(updates);



                                        Map<String, Object> map = new HashMap<>();
                                        map.put("idActions",1);
                                        map.put("IdOpporunite", ld.getId_oppertuntiy());
                                        map.put("Date", currentDate);
                                        map.put("nameOp",ld.getOpport());
                                        map.put("id_status_actions", status_name_selected);
                                        map.put("userId", userId);
                                        FirebaseDatabase.getInstance().getReference().child("Actions").push()
                                                .setValue(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(mContext, "update Success" , Toast.LENGTH_SHORT).show();

                                                        ((Activity)mContext).finish();

                                                        mContext.startActivity(new Intent(mContext, OpportunitiesStatus.class));

                                                    }

                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(mContext, "update not Success" , Toast.LENGTH_SHORT).show();

                                                    }
                                                });



                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        };

                        demografiRef.addListenerForSingleValueEvent(valueEventListener);
                    }
                });


                dialogPlus.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return listDatumOpps.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtid, txtname, proprey ,user_name_op,name_client,date_op;
        LinearLayout editStatus;
        Spinner items_Status;

        public ViewHolder(View itemView) {
            super(itemView);
            txtid = (TextView) itemView.findViewById(R.id.opper_name);
            txtname = (TextView) itemView.findViewById(R.id.espere_name);
            proprey = (TextView) itemView.findViewById(R.id.txtprobabilite_row);
            editStatus = itemView.findViewById(R.id.edit_status);
            user_name_op=itemView.findViewById(R.id.user_name_op);
            name_client=itemView.findViewById(R.id.name_client);
            date_op=itemView.findViewById(R.id.date_op);
//            items_Status = (Spinner) itemView.findViewById(R.id.spinner_fragment2);
        }
    }


}

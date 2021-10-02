package com.CRM.opportunity.opportunityMain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CRM.opportunity.AddOppertunite;
import com.CRM.opportunity.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

public class myadapterOpp extends FirebaseRecyclerAdapter<Oprentity, myadapterOpp.myviewholder> implements OnItemClickListener {
    StorageReference storageReference;
    String userId;
    FirebaseAuth fAuth;
    Context mContext;
    public myadapterOpp(@NonNull FirebaseRecyclerOptions<Oprentity> options) {

        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Oprentity Oprentity) {
        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();



        holder.user_name.setVisibility(View.GONE);
        holder.txtid.setText(Oprentity.getOpport());
        holder.txtname.setText(Oprentity.getEspere());
        holder.proprey.setText(Oprentity.getTxtprobabilite());
        holder.date_op.setText(Oprentity.getFermeture());
        holder.name_client.setText(Oprentity.getName_customer());




        holder.editStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(view.getContext(), holder.editStatus);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu1:
                                //handle menu1 click
                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.txtname.getContext());
                                builder.setTitle("Delete");
                                builder.setMessage("Are you sure Delete ...?");

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase.getInstance().getReference().child("oppertunite")
                                                .child(getRef(holder.getAdapterPosition()).getKey()).removeValue();

                                    }
                                });

                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                                builder.show();
                                break;
                            case R.id.menu2:
                                Intent intent = new Intent(view.getContext(), AddOppertunite.class);
                                intent.putExtra("statusedit","2");
                                intent.putExtra("opport",Oprentity.getOpport());
                                intent.putExtra("espere",Oprentity.getEspere());
                                intent.putExtra("txtprobabilite",Oprentity.getTxtprobabilite());
                                intent.putExtra("fermeture",Oprentity.getFermeture());
                                intent.putExtra("name_customer",Oprentity.getName_customer());
                                intent.putExtra("id_status",Oprentity.getId_status());
                                intent.putExtra("id_oppertuntiy",Oprentity.getId_oppertuntiy());
                                intent.putExtra("userId",Oprentity.getUserId());
                                intent.putExtra("position",getRef(holder.getAdapterPosition()).getKey());

                                mContext.startActivity(intent);

                                ((Activity)mContext).finish();

                                break;

                        }
                        return false;
                    }

//                                @Override
//                                public boolean onMenuItemClick(MenuItem item) {
//                                    switch (item.getItemId()) {
//                                        case R.id.menu1:
//                                            //handle menu1 click
//                                            break;
//                                        case R.id.menu2:
//                                            //handle menu2 click
//                                            break;
//                                        case R.id.menu3:
//                                            //handle menu3 click
//                                            break;
//                                    }
//                                    return false;
//                                }
                });
                //displaying the popup
                popup.show();
            }
        });


    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opportunity_row, parent, false);
        mContext=parent.getContext();
        return new myviewholder(view);
    }

    @Override
    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
        Toast.makeText(view.getContext(), "you selected is "+position, Toast.LENGTH_SHORT).show();
    }


    class myviewholder extends RecyclerView.ViewHolder {
        private TextView txtid, txtname, proprey ,user_name_op,name_client,date_op;
        LinearLayout editStatus,user_name;
        Spinner items_Status;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            txtid = (TextView) itemView.findViewById(R.id.opper_name);
            txtname = (TextView) itemView.findViewById(R.id.espere_name);
            proprey = (TextView) itemView.findViewById(R.id.txtprobabilite_row);
            editStatus = itemView.findViewById(R.id.edit_status);
            user_name = itemView.findViewById(R.id.user_name);
            user_name_op=itemView.findViewById(R.id.user_name_op);
            name_client=itemView.findViewById(R.id.name_client);
            date_op=itemView.findViewById(R.id.date_op);
        }
    }
}

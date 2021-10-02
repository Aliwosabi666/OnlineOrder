package com.CRM.opportunity.Status;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CRM.opportunity.R;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;


public class myadapterStatus extends FirebaseRecyclerAdapter<StatusClas, myadapterStatus.myviewholder> implements OnItemClickListener {
    StorageReference storageReference;
    String userId;
    FirebaseAuth fAuth;

    public myadapterStatus(@NonNull FirebaseRecyclerOptions<StatusClas> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final StatusClas StatusClas) {
        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        holder.name_status.setText(StatusClas.getName());
        holder.status_option_menue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(view.getContext(), holder.status_option_menue);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu_status);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.menu1:
                                //handle menu1 click
                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name_status.getContext());
                                builder.setTitle("Delete Panel");
                                builder.setMessage("Delete...?");

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase.getInstance().getReference().child("oppertunitestate")
                                                .child(getRef(position).getKey()).removeValue();
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
                                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name_status.getContext())
                                        .setContentHolder(new ViewHolder(R.layout.dialogcontent_satus))
                                        .setExpanded(true, 1200)
                                        .create();
                                View myview = dialogPlus.getHolderView();
                                // final EditText purl=myview.findViewById(R.id.uimgurl);
                                final EditText name_satus = myview.findViewById(R.id.unom_satus);
                                Button submit = myview.findViewById(R.id.usubmit_update_status);

                                name_satus.setText(StatusClas.getName());
                                dialogPlus.show();
                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Map<String, Object> map = new HashMap<>();
                                        //map.put("purl",purl.getText().toString());
                                        map.put("name", name_satus.getText().toString());

                                        FirebaseDatabase.getInstance().getReference().child("oppertunitestate")
                                                .child(getRef(position).getKey()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialogPlus.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        dialogPlus.dismiss();
                                                    }
                                                });
                                    }
                                });
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_status, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
//        Toast.makeText(view.getContext(), "you selected is "+position, Toast.LENGTH_SHORT).show();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        ImageView edit, delete;
        TextView name_status;
        LinearLayout status_option_menue;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name_status = (TextView) itemView.findViewById(R.id.nomtext_status);
            status_option_menue = itemView.findViewById(R.id.status_option_menue);
        }
    }
}

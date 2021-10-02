package com.CRM.opportunity.client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CRM.opportunity.AddOppertunite;
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
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class myadapter extends FirebaseRecyclerAdapter<Client, myadapter.myviewholder> implements OnItemClickListener {
    StorageReference storageReference;
    String userId;
    FirebaseAuth fAuth;
    Context mContext;
    public myadapter(@NonNull FirebaseRecyclerOptions<Client> options) {

        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Client Client) {
        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

//        StorageReference profileRef = storageReference.child("Client/" + Client.getImgUrl() + "/profileclient.jpg");
//        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(holder.img);
//            }
//        });




//        Picasso.with().load(uri).into(profileImageView);
//        Picasso.with().load(uri).into(profileImageView);
        Picasso.get().load(Client.getImgUrl()).placeholder(R.drawable.user_image).into(holder.img);

        holder.vnom.setText(Client.getNom() + " " + Client.getPrenom());
//       holder.vprenom.setText(Client.getPrenom());
        holder.vadresse.setText(Client.getAdresse());
        holder.vemail.setText(Client.getEmail());
        holder.vcoorgps.setText(Client.getCoor_gps());

        /*   Glide.with(holder.name.getContext()).load(Client.getPurl()).into(holder.img);*/





        holder.client_option_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(view.getContext(), holder.client_option_menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu1:
                                //handle menu1 click
                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.vnom.getContext());
                                builder.setTitle("Delete Panel");
                                builder.setMessage("Delete...?");

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase.getInstance().getReference().child("Client")

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

                                Intent intent = new Intent(view.getContext(), AddClient.class);
                                intent.putExtra("nom",Client.getNom());
                                intent.putExtra("prenom",Client.getPrenom());
                                intent.putExtra("adresse",Client.getAdresse());
                                intent.putExtra("email",Client.getEmail());
                                intent.putExtra("coor_gps",Client.getCoor_gps());
                                intent.putExtra("imgUrl",Client.getImgUrl());
                                intent.putExtra("userId",Client.getUserId());
                                intent.putExtra("position",getRef(holder.getAdapterPosition()).getKey());

                                mContext.startActivity(intent);

                                ((Activity)mContext).finish();



//                                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.vnom.getContext())
//                                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
//                                        .setExpanded(true, 1200)
//                                        .create();
//                                View myview = dialogPlus.getHolderView();
//                                // final EditText purl=myview.findViewById(R.id.uimgurl);
//                                final EditText nom = myview.findViewById(R.id.unom);
//                                final EditText prenom = myview.findViewById(R.id.uprenom);
//                                final EditText adresse = myview.findViewById(R.id.uadresse);
//                                final EditText email = myview.findViewById(R.id.uemail);
//                                final EditText coor_gps = myview.findViewById(R.id.ucoor_gps);
//                                Button submit = myview.findViewById(R.id.usubmit);
//
//                                //purl.setText(Client.getPurl());
//                                nom.setText(Client.getNom());
//                                prenom.setText(Client.getPrenom());
//                                adresse.setText(Client.getAdresse());
//                                email.setText(Client.getEmail());
//                                coor_gps.setText(Client.getCoor_gps());
//
//                                dialogPlus.show();
//
//                                submit.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Map<String, Object> map = new HashMap<>();
//                                        //map.put("purl",purl.getText().toString());
//                                        map.put("nom", nom.getText().toString());
//                                        map.put("prenom", prenom.getText().toString());
//                                        map.put("adresse", adresse.getText().toString());
//                                        map.put("email", email.getText().toString());
//                                        map.put("coor_gps", coor_gps.getText().toString());
//
//                                        FirebaseDatabase.getInstance().getReference().child("Client")
//                                                .child(getRef(position).getKey()).updateChildren(map)
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void aVoid) {
//                                                        dialogPlus.dismiss();
//                                                    }
//                                                })
//                                                .addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        dialogPlus.dismiss();
//                                                    }
//                                                });
//                                    }
//                                });

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        mContext=parent.getContext();
        return new myviewholder(view);
    }

    @Override
    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
        Toast.makeText(view.getContext(), "you selected is "+position, Toast.LENGTH_SHORT).show();
    }


    class myviewholder extends RecyclerView.ViewHolder {
        CircleImageView img;
        ImageView edit, delete;
        TextView vnom, vprenom, vadresse, vemail, vcoorgps;
        LinearLayout client_option_menu;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img1);
            vnom = (TextView) itemView.findViewById(R.id.nomtext);
            vprenom = (TextView) itemView.findViewById(R.id.prenometext);
            vadresse = (TextView) itemView.findViewById(R.id.adressetext);
            vemail = (TextView) itemView.findViewById(R.id.emailtext);
            vcoorgps = (TextView) itemView.findViewById(R.id.coor_gpstext);

            edit = (ImageView) itemView.findViewById(R.id.editicon);
            delete = (ImageView) itemView.findViewById(R.id.deleteicon);
            client_option_menu = itemView.findViewById(R.id.clinent_option);
        }
    }
}

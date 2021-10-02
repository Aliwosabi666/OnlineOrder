package com.CRM.opportunity.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.CRM.opportunity.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyadapterUsers extends FirebaseRecyclerAdapter<Users, MyadapterUsers.myviewholder> {
    StorageReference storageReference;
    FirebaseAuth fAuth;
    private String userId;


    public MyadapterUsers(@NonNull FirebaseRecyclerOptions<Users> options) {

        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Users Users) {
        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        holder.user_name.setText(Users.getName());
//       holder.vprenom.setText(Users.getPrenom());
        holder.user_phone.setText(Users.getPhone());

        /*   Glide.with(holder.name.getContext()).load(Users.getPurl()).into(holder.img);*/






//        holder.userOption.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                //creating a popup menu
//                PopupMenu popup = new PopupMenu(view.getContext(), holder.userOption);
//                //inflating menu from xml resource
//                popup.inflate(R.menu.options_menu);
//                //adding click listener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        switch (menuItem.getItemId()) {
//                            case R.id.menu1:
//                                //handle menu1 click
//                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name_talior.getContext());
//                                builder.setTitle("Delete user Acount ");
//                                builder.setMessage("Delete ...؟");
//
//                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        FirebaseDatabase.getInstance().getReference().child("Users").child(userId)
//                                                .child(getRef(position).getKey()).removeValue();
//                                    }
//                                });
//
//                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    }
//                                });
//
//                                builder.show();
//                                break;
//                            case R.id.menu2:
////                                final DialogPlus dialogPlus_talior = DialogPlus.newDialog(holder.name_talior.getContext())
////                                        .setContentHolder(new ViewHolder(R.layout.dialogcontenttalior))
////                                        .setExpanded(true, 1200)
////                                        .create();
////
////                                View myview_dialog_talior = dialogPlus_talior.getHolderView();
////                                // final EditText purl=myview.findViewById(R.id.uimgurl);
////                                final TextInputEditText name = myview_dialog_talior.findViewById(R.id.name_talior_update);
////                                final TextInputEditText phone = myview_dialog_talior.findViewById(R.id.phone_talior_update);
////                                Button save_udpate_talior = myview_dialog_talior.findViewById(R.id.save_udpate_edit);
////                                LinearLayout back_dialog_taliior = myview_dialog_talior.findViewById(R.id.back_dialog_taliior);
////
////                                name.setText(Users.getName());
////                                phone.setText(Users.getPhone());
////                                dialogPlus_talior.show();
////
////                                back_dialog_taliior.setOnClickListener(new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View view) {
////                                        dialogPlus_talior.dismiss();
////                                    }
////                                });
////
////                                save_udpate_talior.setOnClickListener(new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View view) {
////
////                                        Map<String, Object> map = new HashMap<>();
////                                        //map.put("purl",purl.getText().toString());
////                                        map.put("name", name.getText().toString());
////                                        map.put("phone", phone.getText().toString());
////                                        map.put("password", Users.getPassword());
////                                        map.put("userId", Users.getUserId());
////                                        map.put("status", Users.getStatus());
////
////                                        FirebaseDatabase.getInstance().getReference().child("Users").child(userId)
////                                                .child(getRef(position).getKey()).updateChildren(map)
////                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
////                                                    @Override
////                                                    public void onSuccess(Void aVoid) {
////                                                        dialogPlus_talior.dismiss();
////                                                    }
////                                                })
////                                                .addOnFailureListener(new OnFailureListener() {
////                                                    @Override
////                                                    public void onFailure(@NonNull Exception e) {
////                                                        dialogPlus_talior.dismiss();
////                                                    }
////                                                });
////                                    }
////                                });
////
////                                break;
//
//                        }
//                        return false;
//                    }
//
//                });
//                //displaying the popup
//                popup.show();
//            }
//        });


    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_users, parent, false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder {
        CircleImageView image_talior;
//        ImageView edit, delete;
        TextView user_name, user_phone;
//        LinearLayout userOption;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
//            image_talior = (CircleImageView) itemView.findViewById(R.id.image_talior_circle);
//            name_talior = (TextView) itemView.findViewById(R.id.name_talior_circle);
//            phone_talior = (TextView) itemView.findViewById(R.id.phone_talior_cirle);


//            edit = (ImageView) itemView.findViewById(R.id.editicon);
//            delete = (ImageView) itemView.findViewById(R.id.deleteicon);

        }
    }
}

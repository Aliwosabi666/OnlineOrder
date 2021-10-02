package com.CRM.opportunity.Action;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CRM.opportunity.R;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;


public class myadapterActions extends FirebaseRecyclerAdapter<ActionsClass, myadapterActions.myviewholder> implements OnItemClickListener {
    StorageReference storageReference;
    String userId;
    FirebaseAuth fAuth;

    public myadapterActions(@NonNull FirebaseRecyclerOptions<ActionsClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final ActionsClass ActionsClass) {
        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        holder.nameActionStatus.setText(ActionsClass.getNameOp());
        holder.date_actions.setText(ActionsClass.getDate());
        holder.name_status.setText(ActionsClass.getId_status_actions());

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_actions, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
//        Toast.makeText(view.getContext(), "you selected is "+position, Toast.LENGTH_SHORT).show();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        ImageView nameOp, date ,idstatus;
        TextView nameActionStatus ,date_actions , name_status;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            nameActionStatus = (TextView) itemView.findViewById(R.id.nomtext_actions);
            date_actions=itemView.findViewById(R.id.date_actions);
            name_status=itemView.findViewById(R.id.name_status);

        }
    }
}

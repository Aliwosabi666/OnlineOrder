package com.CRM.opportunity.users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.CRM.opportunity.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Users> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<Users> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.singlerow_users, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Users users = mData.get(position);

//        if (users.getName().equals("admin")){
//            return;
//        }

        holder.myTextView.setText(users.getName());
        holder.phone_number.setText(users.getPhone());
        holder.type_acount.setText(users.getStatus());
        String userStatus= String.valueOf(users.getUserStatus());

        holder.user_status.setText(userStatus);
//
//        holder.enable_admin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                FirebaseUser user;
//                FirebaseFirestore fStore;
//                fStore = FirebaseFirestore.getInstance();
//
//
//
//
//
//
//
//
//
//
//
//            }
//        });

//        holder.talior_option_menue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//
//            }
//        });



    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView,phone_number,user_status , type_acount;
        LinearLayout talior_option_menue;
        Button enable_admin;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.users_name);
            phone_number=itemView.findViewById(R.id.phone_number_user);
            user_status=itemView.findViewById(R.id.user_status);
            type_acount=itemView.findViewById(R.id.type_acoutn);

//            enable_admin=itemView.findViewById(R.id.enable_admin);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
//    String getItem(int id) {
//        return mData.get(id);
//    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
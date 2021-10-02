package com.CRM.opportunity.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.CRM.opportunity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UsersMain extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    RecyclerView recviewtaliors;
    MyRecyclerViewAdapter adapter;
    private FloatingActionButton fb;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    private String userId;
    Button saveBtn;
    FirebaseUser user;
    FirebaseFirestore fStore;
    List<String> arrayListUsers = new ArrayList<>();
    List<Users> arrayListUserssecond = new ArrayList<>();
    FirebaseFirestore db;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_main);
        setTitle("            Users");


//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setElevation(15);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        adapter = new MyRecyclerViewAdapter(UsersMain.this, arrayListUserssecond);
        db = FirebaseFirestore.getInstance();


        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();


//        adapter.notifyDataSetChanged();

        recviewtaliors = findViewById(R.id.recview_taliors);
//        recviewtaliors.setLayoutManager(new LinearLayoutManager(UsersMain.this));
        recviewtaliors.setLayoutManager(new LinearLayoutManager(this));


        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                arrayListUsers.add(document.getDate());
//                                Log.d("TAG", document.getId() + " => " + document.getData());
//                                Log.d("TAGName", ""+ document.get("email"));
                                String name = String.valueOf(document.get("fName"));
                                String email = String.valueOf(document.get("email"));
                                String phone = String.valueOf(document.get("phone"));
                                String status = String.valueOf(document.get("status"));
                                boolean userStatus = (boolean) document.get("user_status");
                                String userId = document.getId();

                                users = new Users(name, phone, email, userId, status, userStatus);
                                arrayListUserssecond.add(users);

                            }


                            recviewtaliors.setAdapter(adapter);

                            adapter.notifyDataSetChanged();

                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }


                    }

                });


        adapter.setClickListener(this);


//        FirebaseRecyclerOptions<Users> options =
//                new FirebaseRecyclerOptions.Builder<Users>()
//                        .setQuery(db.collection(), Users.class)
//                        .build();
//
//        adapter = new MyadapterUsers(options);
//        recviewtaliors.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View view, int position) {

        final Users users_info = arrayListUserssecond.get(position);

//        Toast.makeText(this, "user name is "+users_info.getName(), Toast.LENGTH_SHORT).show();
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//
//        // set title
//        alertDialogBuilder.setTitle("User account status "+users_info.getName() +"?");
//
//        // set dialog message
//        alertDialogBuilder
////                .setMessage("هل انت متأكد من تفعيل المستخدم؟")
//                .setCancelable(true)
//                .setPositiveButton("activate", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
////                        Toast.makeText(UsersMain.this, "user name "+users.getName(), Toast.LENGTH_SHORT).show();
//
//                        DocumentReference docRef = fStore.collection("users").document(users_info.getUserId());
//                        Map<String, Object> edited = new HashMap<>();
//                        edited.put("email", users_info.getEmail());
//                        edited.put("fName", users_info.getName());
//                        edited.put("phone", users_info.getPhone());
//                        edited.put("status","true");
//                        edited.put("user_status",true);
//                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(UsersMain.this, "The account has been successfully activated", Toast.LENGTH_SHORT).show();
//                                finish();
//                                startActivity(getIntent());
////                                adapter.notifyDataSetChanged();
//                            }
//                        });
//
//                    }
//                })
//                .setNegativeButton("Disable ",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//
//                        DocumentReference docRef = fStore.collection("users").document(users_info.getUserId());
//                        Map<String, Object> edited = new HashMap<>();
//                        edited.put("email", users_info.getEmail());
//                        edited.put("fName", users_info.getName());
//                        edited.put("phone", users_info.getPhone());
//                        edited.put("status","false");
//                        edited.put("user_status",false);
//                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(UsersMain.this, "Account disabled successfully", Toast.LENGTH_SHORT).show();
//                              finish();
//                              startActivity(getIntent());
////
////                                startActivity(new Intent(getApplicationContext(), UserActivity.class));
////                                finish();
//                            }
//                        });
////                        dialog.cancel();
//                    }
//                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//
//        // create alert dialog
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // show it
//        alertDialog.show();


        new SweetAlertDialog(UsersMain.this, SweetAlertDialog.WARNING_TYPE)
               .setTitleText("Type A count user!")
                .setContentText("Choose the type of permission you want to assign to the user " + users_info.getName()+" ?")
//                        .setContentText("You won't be able to recover this file!")
                .setConfirmText("Admin")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        new SweetAlertDialog(UsersMain.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Admin A Count!")
                                .setConfirmText("activate")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();


                                        DocumentReference docRef = fStore.collection("users").document(users_info.getUserId());
                                        Map<String, Object> edited = new HashMap<>();
                                        edited.put("email", users_info.getEmail());
                                        edited.put("fName", users_info.getName());
                                        edited.put("phone", users_info.getPhone());
                                        edited.put("status", "true");
                                        edited.put("user_status", users_info.getUserStatus());
                                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
//
                                                new SweetAlertDialog(UsersMain.this, SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("Change Acount Admin "+users_info.getName() +" successfully ")
                                                        .setConfirmText("Ok")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {
                                                                sDialog.dismissWithAnimation();
                                                                finish();
                                                                startActivity(getIntent());

                                                            }
                                                        })
                                                        .show();


                                            }
                                        });


                                    }
                                })
                                .setCancelButton("Deactivate", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        sDialog.dismissWithAnimation();


                                        DocumentReference docRef = fStore.collection("users").document(users_info.getUserId());
                                        Map<String, Object> edited = new HashMap<>();
                                        edited.put("email", users_info.getEmail());
                                        edited.put("fName", users_info.getName());
                                        edited.put("phone", users_info.getPhone());
                                        edited.put("status", "false");
                                        edited.put("user_status", users_info.getUserStatus());
                                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
//
                                                new SweetAlertDialog(UsersMain.this, SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("Change Acount Admin "+users_info.getName() +" successfully ")
                                                        .setConfirmText("Ok")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {
                                                                sDialog.dismissWithAnimation();
                                                                finish();
                                                                startActivity(getIntent());

                                                            }
                                                        })
                                                        .show();



                                            }
                                        });

                                    }
                                })
                                .show();


                    }
                })
                .setCancelButton("Acount", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        new SweetAlertDialog(UsersMain.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("User A Count!")
                                .setConfirmText("activate")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();

                                        DocumentReference docRef = fStore.collection("users").document(users_info.getUserId());
                                        Map<String, Object> edited = new HashMap<>();
                                        edited.put("email", users_info.getEmail());
                                        edited.put("fName", users_info.getName());
                                        edited.put("phone", users_info.getPhone());
                                        edited.put("status", users_info.getStatus());
                                        edited.put("user_status", true);
                                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
//
                                                new SweetAlertDialog(UsersMain.this, SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("activation Acount user "+users_info.getName()+" successfully ")
                                                        .setConfirmText("Ok")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {
                                                                sDialog.dismissWithAnimation();
                                                                finish();
                                                                startActivity(getIntent());
                                                            }
                                                        })
                                                        .show();


                                            }
                                        });





                                    }
                                })
                                .setCancelButton("Deactivate", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        DocumentReference docRef = fStore.collection("users").document(users_info.getUserId());
                                        Map<String, Object> edited = new HashMap<>();
                                        edited.put("email", users_info.getEmail());
                                        edited.put("fName", users_info.getName());
                                        edited.put("phone", users_info.getPhone());
                                        edited.put("status", users_info.getStatus());
                                        edited.put("user_status", false);
                                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
//
                                                new SweetAlertDialog(UsersMain.this, SweetAlertDialog.SUCCESS_TYPE)
                                                        .setTitleText("Deactivate Acount user "+users_info.getName()+" successfully ")
                                                        .setConfirmText("Ok")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {
                                                                finish();
                                                                startActivity(getIntent());
                                                                sDialog.dismissWithAnimation();

                                                            }
                                                        })
                                                        .show();


                                            }
                                        });




                                    }
                                })
                                .show();


                    }
                })
                .show();

//                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("Are you sure?")
//                        .setContentText("Won't be able to recover this file!")
//                        .setConfirmText("Yes,delete it!")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog
//                                        .setTitleText("Deleted!")
//                                        .setContentText("Your imaginary file has been deleted!")
//                                        .setConfirmText("OK")
//                                        .setConfirmClickListener(null)
//                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                            }
//                        })
//                        .show();


    }

    @Override
    protected void onStart() {
//        adapter.notify();


//        recviewtaliors.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        super.onStart();
    }

    @Override
    protected void onResume() {
//        adapter.notify();
//        adapter = new MyRecyclerViewAdapter(this, arrayListUserssecond);
//        adapter.setClickListener(this);
//        recviewtaliors.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onPause() {
//        adapter = new MyRecyclerViewAdapter(this, arrayListUserssecond);
//        adapter.setClickListener(this);
//        recviewtaliors.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        super.onPause();
    }

   

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
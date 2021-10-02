package com.CRM.opportunity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.CRM.opportunity.Action.ActionsActivity;
import com.CRM.opportunity.oppertunites.OpportunitiesStatus;
import com.CRM.opportunity.opportunityMain.OprotunityActivityMain;
import com.CRM.opportunity.userAcounts.Login;
import com.CRM.opportunity.userAcounts.UserActivity;
import com.CRM.opportunity.users.UsersMain;
import com.CRM.opportunity.R;
import com.CRM.opportunity.Status.StatusActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import javax.annotation.Nullable;

public class NavigationDrawer extends AppCompatActivity {

    //    private AppBarConfiguration mAppBarConfiguration;
    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    NavigationView navigationView;
    String userId;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser currentUser ;
    StorageReference storageReference;
    ImageView navUserPhot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        initInstances();
        checkStatusUser();

        userId = fAuth.getCurrentUser().getUid();

        // ini

        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

//        DocumentReference documentReference = fStore.collection("users").document(userId);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                if(documentSnapshot.exists()){
//                    String idState=documentSnapshot.getString("status");
//
//                    SharedPreferences userInfo = getSharedPreferences("userInfo", 0);
//                    final SharedPreferences.Editor editor = userInfo.edit();
//                    editor.putString("id_status",idState);
//                    editor.commit();
//
//
////                    if (idState.equals("false")){
////
////
////
////                        Toast.makeText(NavigationDrawer.this, "the id is: "+idState, Toast.LENGTH_SHORT).show();
//////                        FirebaseAuth.getInstance().signOut();//logout
//////                        startActivity(new Intent(getApplicationContext(), Login.class));
//////                        finish();
////
////                    }
//
//
//
//
//                }else {
//                    Log.d("tag", "onEvent: Document do not exists");
//                }
//            }
//        });


        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId = test_user.getString("id_status", "");
        String name = test_user.getString("name", "");
        String email = test_user.getString("email", "");

        if (checkId == "false") {
//            Toast.makeText(this, "you are is :"+checkId, Toast.LENGTH_SHORT).show();
            hideItem();

        }
        if (checkId == "true") {
            displayItem();
        }

        updateNavHeader();

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId = test_user.getString("id_status", "");

        if (checkId.equals("false") || checkId == "false") {
//            Toast.makeText(this, "you are is :"+checkId, Toast.LENGTH_SHORT).show();
            hideItem();

        } else {
            displayItem();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId = test_user.getString("id_status", "");

        if (checkId.equals("false") || checkId == "false") {
//            Toast.makeText(this, "you are is :"+checkId, Toast.LENGTH_SHORT).show();
            hideItem();

        } else {
            displayItem();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId = test_user.getString("id_status", "");

        if (checkId.equals("false") || checkId == "false") {
//            Toast.makeText(this, "you are is :"+checkId, Toast.LENGTH_SHORT).show();
            hideItem();

        } else
            displayItem();
    }

    @Override
    protected void onResume() {
        updateNavHeader();
        super.onResume();
        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId = test_user.getString("id_status", "");

        if (checkId.equals("false") || checkId == "false") {
//            Toast.makeText(this, "you are is :"+checkId, Toast.LENGTH_SHORT).show();
            hideItem();

        } else
            displayItem();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId = test_user.getString("id_status", "");

        if (checkId.equals("false") || checkId == "false") {
//            Toast.makeText(this, "you are is :"+checkId, Toast.LENGTH_SHORT).show();
            hideItem();

        } else
            displayItem();
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(NavigationDrawer.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setCheckedItem(R.id.nav_home);


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        Intent intent = new Intent(NavigationDrawer.this, NavigationDrawer.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_display_aciton:
                        Intent i = new Intent(NavigationDrawer.this, ActionsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_clients:
                        Intent clients = new Intent(NavigationDrawer.this, ClientsMain.class);
                        startActivity(clients);
                        break;
                    case R.id.nav_add_oppertunite:
                        Intent add_opp = new Intent(NavigationDrawer.this, AddOppertunite.class);
                        startActivity(add_opp);
                        break;

                    case R.id.nav_slideshow:
                        Intent intent_slidesho = new Intent(NavigationDrawer.this, OpportunitiesStatus.class);
                        startActivity(intent_slidesho);
                        break;

                    case R.id.all_status:
                        Intent intent_all_status = new Intent(NavigationDrawer.this, StatusActivity.class);
                        startActivity(intent_all_status);
                        break;


                    case R.id.profile_user:
                        startActivity(new Intent(NavigationDrawer.this, UserActivity.class));
                        break;

                    case R.id.all_user:
                        startActivity(new Intent(NavigationDrawer.this, UsersMain.class));
                        break;

                    case R.id.nav_op:
                        startActivity(new Intent(NavigationDrawer.this, OprotunityActivityMain.class));
                        break;

                    case R.id.log_out:
                        logOut();
                        break;

                }
                return false;
            }
        });

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.string.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
//        nav_Menu.findItem(R.id.nav_home).setVisible(false);
//        nav_Menu.findItem(R.id.nav_add_client).setVisible(false);
//        nav_Menu.findItem(R.id.nav_clients).setVisible(false);
//        nav_Menu.findItem(R.id.nav_add_oppertunite).setVisible(false);
//        nav_Menu.findItem(R.id.nav_slideshow).setVisible(false);
        nav_Menu.findItem(R.id.all_user).setVisible(false);
        nav_Menu.findItem(R.id.all_status).setVisible(false);


    }

    private void displayItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_home).setVisible(true);
        nav_Menu.findItem(R.id.nav_clients).setVisible(true);
        nav_Menu.findItem(R.id.nav_add_oppertunite).setVisible(true);
        nav_Menu.findItem(R.id.nav_slideshow).setVisible(true);
        nav_Menu.findItem(R.id.profile_user).setVisible(true);
        nav_Menu.findItem(R.id.nav_display_aciton).setVisible(true);
    }

    void checkStatusUser() {
        FirebaseFirestore fStore;
        FirebaseAuth fAuth;
        String userId;

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {

                    boolean checkidstatus = documentSnapshot.getBoolean("user_status");

                    if (!checkidstatus) {
                        Toast.makeText(NavigationDrawer.this, " Connect with admin to enable a count ", Toast.LENGTH_SHORT).show();
                        logOut();
                    }

                } else {
                    Log.d("thetokenis16", "checkStatusUser: ");
                }
            }
        });
//        Log.d("thetokenis16", "checkStatusUser: " + idstatus);
    }

    void logOut() {
        SharedPreferences preferences =getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
//        finish();

        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // or finish();
    }

    public void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_username);
        TextView navUserMail = headerView.findViewById(R.id.nav_user_mail);
        navUserPhot = headerView.findViewById(R.id.nav_user_photo);

        navUserPhot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });

        navUserMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });
        navUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences test_user = getSharedPreferences("userInfo", 0);
        String checkId = test_user.getString("id_status", "");
        String name = test_user.getString("fName", "");
        String email = test_user.getString("email", "");

        navUserMail.setText(email);
        navUsername.setText(name);

        // now we will use Glide to load user image
        // first we need to import the library

        //Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserPhot);

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(navUserPhot);
            }
        });
    }


}
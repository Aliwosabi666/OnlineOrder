package com.CRM.opportunity.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.CRM.opportunity.ClientsMain;
import com.CRM.opportunity.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddClient extends AppCompatActivity {
    EditText nom, prenom, adresse, email, coor_gps;
    Button submit, back;
    ImageView profileImageView;
    StorageReference storageReference;
    String urlImage;
    StorageReference fileRef;
    FirebaseUser user;
    String userId;
    FirebaseAuth fAuth;
    SweetAlertDialog pDialog;
    public static final int RequestPermissionCode = 1;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);


        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Client");
        mDatabase.keepSynced(true);

        nom = (EditText) findViewById(R.id.add_nom);
        prenom = (EditText) findViewById(R.id.add_prenom);
        adresse = (EditText) findViewById(R.id.add_adresse);
        email = (EditText) findViewById(R.id.add_email);
        coor_gps = (EditText) findViewById(R.id.add_coor_gps);
        profileImageView = findViewById(R.id.image_customer);
        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();

        userId = fAuth.getCurrentUser().getUid();
//        user = fAuth.getCurrentUser();


        if (getIntent().getExtras() != null) {

            nom.setText(getIntent().getStringExtra("nom"));
            prenom.setText(getIntent().getStringExtra("prenom"));
            adresse.setText(getIntent().getStringExtra("adresse"));
            email.setText(getIntent().getStringExtra("email"));
            coor_gps.setText(getIntent().getStringExtra("coor_gps"));
            String  imgUrl = getIntent().getStringExtra("imgUrl");

            Picasso.get().load(imgUrl).placeholder(R.drawable.user_image).into(profileImageView);
            String  userId = getIntent().getStringExtra("userId");
            String  postion = getIntent().getStringExtra("position");

        }





//        EnableRuntimePermission();
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
//                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 7);
            }
        });
        submit = (Button) findViewById(R.id.add_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }
        });
    }





    private void processinsert() {
        if (nom.length() == 0) {
            nom.setError("Plz enter your name");
        } else if (prenom.length() == 0) {
            prenom.setError("Plz enter your prenom");
        } else if (adresse.length() == 0) {
            adresse.setError("Plz enter your addresse");
        } else if (email.length() == 0) {
            email.setError("Plz enter your email");
        } else if (coor_gps.length() == 0) {
            coor_gps.setError("Plz enter your coor gps");
        } else {


            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#0D5C78"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(true);
            pDialog.show();


            if (getIntent().getExtras() != null) {

                String  postion = getIntent().getStringExtra("position");

                Map<String, Object> map = new HashMap<>();
                //map.put("purl",purl.getText().toString());
                map.put("nom", nom.getText().toString());
                map.put("prenom", prenom.getText().toString());
                map.put("adresse", adresse.getText().toString());
                map.put("email", email.getText().toString());
                map.put("coor_gps", coor_gps.getText().toString());
                map.put("imgUrl", urlImage);

                FirebaseDatabase.getInstance().getReference().child("Client")
                        .child(postion).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pDialog.dismissWithAnimation();
                                Toast.makeText(AddClient.this, " Update Data successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ClientsMain.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pDialog.dismissWithAnimation();
                                Toast.makeText(AddClient.this, "No Update Data", Toast.LENGTH_SHORT).show();

                            }
                        });

            }else {

                Map<String, Object> map = new HashMap<>();
                map.put("nom", nom.getText().toString());
                map.put("prenom", prenom.getText().toString());
                map.put("adresse", adresse.getText().toString());
                map.put("email", email.getText().toString());
                map.put("coor_gps", coor_gps.getText().toString());
                map.put("imgUrl", urlImage);
                map.put("userId",userId);
                FirebaseDatabase.getInstance().getReference().child("Client").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                nom.setText("");
                                prenom.setText("");
                                adresse.setText("");
                                email.setText("");
                                coor_gps.setText("");

                                pDialog.dismissWithAnimation();

                                Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                                finish();
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pDialog.dismissWithAnimation();
                                Toast.makeText(getApplicationContext(), "Could not insert", Toast.LENGTH_LONG).show();
                            }
                        });


                if (isOnline() == false) {
                    Toast.makeText(getApplicationContext(), "Inserted Successfully without connection internet", Toast.LENGTH_LONG).show();
                  //  progressBar_add_client.setVisibility(View.GONE);
                    Intent intent = new Intent(AddClient.this, ClientsMain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("offline", true);
                    startActivity(intent);
                }


            }


        }
    }

    public void save_client(View view) {
        processinsert();

    }

    @Override
    public void onBackPressed()
    {


        super.onBackPressed();

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();

                //profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);


            }
        }

    }

    private void uploadImageToFirebase(final Uri imageUri) {
        // uplaod image to firebase storage
        fileRef = storageReference.child("uploadImagesClients/" + UUID.randomUUID().toString() + "/profileclient.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        urlImage=uri.toString();
                       // Picasso.get().load(uri).into(profileImageView);
                        Picasso.get().load(uri).networkPolicy(NetworkPolicy.OFFLINE).into(profileImageView);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 7 && resultCode == RESULT_OK) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            profileImageView.setImageBitmap(bitmap);
//        }
//    }
    public void EnableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(AddClient.this,
                Manifest.permission.CAMERA)) {
            Toast.makeText(AddClient.this,"CAMERA permission allows us to Access CAMERA app",     Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(AddClient.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] result) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (result.length > 0 && result[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AddClient.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddClient.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void back_client(View view) {
        onBackPressed();
    }

    public boolean isOnline() {
        boolean checkConection;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//For 3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
//For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();
        System.out.println(is3g + " net " + isWifi);

        if (!is3g && !isWifi) {
            checkConection = false;
//            Toast.makeText(getApplicationContext(),"Please make sure your Network Connection is ON ",Toast.LENGTH_LONG).show();
        } else {
            checkConection = true;
        }
        return checkConection;
    }



}




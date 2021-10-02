package com.CRM.opportunity.Status;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.CRM.opportunity.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class StatusActivity extends AppCompatActivity {
    RecyclerView recycervew_status;
    myadapterStatus adapter_status;
    Query query;
    DatabaseReference reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        setTitle("             Status");
        getSupportActionBar().setElevation(15);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycervew_status=findViewById(R.id.recycervew_status);
        recycervew_status.setLayoutManager(new LinearLayoutManager(StatusActivity.this));


        reference2 = FirebaseDatabase.getInstance().getReference();
        reference2.keepSynced(true);
        query = reference2.child("oppertunitestate").orderByChild("check_status").equalTo(true);

        FirebaseRecyclerOptions<StatusClas> options =
                new FirebaseRecyclerOptions.Builder<StatusClas>()
                        .setQuery(query, StatusClas.class)
                        .build();

        adapter_status = new myadapterStatus(options);
        recycervew_status.setAdapter(adapter_status);



    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_status.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter_status.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
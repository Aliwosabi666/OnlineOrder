package com.CRM.opportunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.MenuItem;

import com.CRM.opportunity.OppertunitesStatus.PagerAdapter;
import com.CRM.opportunity.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class OppertunitesActivity extends AppCompatActivity {
    FloatingActionButton add_oppertunite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oppertunites);
        setTitle("CRM");
//        getSupportActionBar().setElevation(40);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addTab(tabLayout.newTab().setText("Qualified"));
        tabLayout.addTab(tabLayout.newTab().setText("Proposition"));
        tabLayout.addTab(tabLayout.newTab().setText("Won"));
        tabLayout.addTab(tabLayout.newTab().setText("Lost"));
//        tabLayout.setTabGravity(TabLayout.TRANSLATION_X);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(OppertunitesActivity.this, "", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                Toast.makeText(OppertunitesActivity.this, "How are you se", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
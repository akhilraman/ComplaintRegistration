package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {
    public static ArrayList arrayList;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    ArrayList<Expert> expertList;
    FirebaseDatabase rootNode;
    DatabaseReference referenceExpert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        tabLayout=findViewById(R.id.tabLayout);

        viewPager=findViewById(R.id.viewpager);
        tabLayout.setupWithViewPager(viewPager);

        VPAdapter vpAdapter= new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new HomeFragment(),"Complaints");
        vpAdapter.addFragment(new ChatFragment(),"chat");

        viewPager.setAdapter(vpAdapter);
    }
}
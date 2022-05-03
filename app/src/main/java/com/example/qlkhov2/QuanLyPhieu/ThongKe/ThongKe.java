package com.example.qlkhov2.QuanLyPhieu.ThongKe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.qlkhov2.R;
import com.google.android.material.tabs.TabLayout;

public class ThongKe extends AppCompatActivity {



//    private TabLayout mTabLayout;


    private ViewPager mviewPager;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);



        mviewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mviewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mviewPager);


    }



    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
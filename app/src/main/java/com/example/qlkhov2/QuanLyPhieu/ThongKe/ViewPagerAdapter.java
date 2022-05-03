package com.example.qlkhov2.QuanLyPhieu.ThongKe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.qlkhov2.QuanLyPhieu.ThongKe.FragmentThongkePN;
import com.example.qlkhov2.QuanLyPhieu.ThongKe.FragmentThongkePX;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new FragmentThongkePN();
            }
            case 1:{
                return new FragmentThongkePX();
            }
            default:
                return new FragmentThongkePN();

        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Phiếu nhâp";
                break;

            case 1:
                title = "Phiếu Xuất";
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

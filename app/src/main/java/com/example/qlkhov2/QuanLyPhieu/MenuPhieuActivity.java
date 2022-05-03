package com.example.qlkhov2.QuanLyPhieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.qlkhov2.Grid_Menu_Adapter;
import com.example.qlkhov2.Item_Menu;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.PhieuNhapActivity;
import com.example.qlkhov2.QuanLyPhieu.PhieuXuat.PhieuXuatActivity;
import com.example.qlkhov2.R;
import com.example.qlkhov2.QuanLyPhieu.ThongKe.ThongKe;

import java.util.ArrayList;
import java.util.List;

public class MenuPhieuActivity extends AppCompatActivity {


    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_phieu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        List<Item_Menu> item_menus = getListMenu();
        this.gridView = (GridView)findViewById(R.id.grid_menu_phieu);
        gridView.setAdapter(new Grid_Menu_Adapter(this,item_menus));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = gridView.getItemAtPosition(i);
                Item_Menu item_menu = (Item_Menu) o;


                if (item_menu.getId() == 1){
                    Intent intent = new Intent(MenuPhieuActivity.this, PhieuNhapActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

                if (item_menu.getId() == 2){
                    Intent intent = new Intent(MenuPhieuActivity.this, PhieuXuatActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

                if (item_menu.getId() == 3){
                    Intent intent = new Intent(MenuPhieuActivity.this, ThongKe.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

//        btnphieunhap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MenuPhieuActivity.this, PhieuNhapActivity.class);
//                startActivity(intent);
////                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });

    }


    private List<Item_Menu> getListMenu(){
        List<Item_Menu> list = new ArrayList<>();
        Item_Menu item1 = new Item_Menu(1, "Phiếu nhập");
        Item_Menu item2 = new Item_Menu(2, "Phiếu xuất");
        Item_Menu item3 = new Item_Menu(3, "Thống kê");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        return list;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
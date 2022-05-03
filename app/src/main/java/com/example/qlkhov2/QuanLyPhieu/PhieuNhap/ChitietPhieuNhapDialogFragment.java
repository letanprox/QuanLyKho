package com.example.qlkhov2.QuanLyPhieu.PhieuNhap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlkhov2.MainActivity;
import com.example.qlkhov2.QuanLyKhachHang.KhachHangActivity;
import com.example.qlkhov2.R;

import java.util.ArrayList;
import java.util.List;

public class ChitietPhieuNhapDialogFragment extends DialogFragment {

    private RecyclerView  rcv_listsp_chitiet_phieunhap;
    private List<PhieuNhap> phieuNhapList;
    private PhieuNhapAdapter phieuNhapAdapter;

    TextView delete_phieunhap;
    IClickDeletePhieuNhap iClickDeletePhieuNhap;

    public ChitietPhieuNhapDialogFragment(List<PhieuNhap> phieuNhapList, IClickDeletePhieuNhap iClickDeletePhieuNhap) {
        this.phieuNhapList = phieuNhapList;
        this.iClickDeletePhieuNhap = iClickDeletePhieuNhap;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_chitiet_phieunhap, container,false);

        delete_phieunhap = view.findViewById(R.id.delete_phieunhap);
        delete_phieunhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle( "Bạn có muốn xóa phieu nay?")
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }}).setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {

                        iClickDeletePhieuNhap.clickItem();

                    }
                }).show();


            }
        });

        rcv_listsp_chitiet_phieunhap = view.findViewById(R.id.rcv_listsp_chitiet_phieunhap);
        phieuNhapAdapter = new PhieuNhapAdapter(phieuNhapList, getContext(), new IClickItemPhieuNhap() {
            @Override
            public void clickItem(PhieuNhap phieuNhap) {

            }
        },false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_listsp_chitiet_phieunhap.setLayoutManager(linearLayoutManager1);
        rcv_listsp_chitiet_phieunhap.setAdapter(phieuNhapAdapter);

        return view;
    }
}

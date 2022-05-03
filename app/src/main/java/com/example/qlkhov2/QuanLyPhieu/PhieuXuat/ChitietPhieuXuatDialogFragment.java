package com.example.qlkhov2.QuanLyPhieu.PhieuXuat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.ChitietPhieuNhapDialogFragment;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.IClickDeletePhieuNhap;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.IClickItemPhieuNhap;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.PhieuNhap;
import com.example.qlkhov2.QuanLyPhieu.PhieuNhap.PhieuNhapAdapter;
import com.example.qlkhov2.R;

import java.util.ArrayList;
import java.util.List;

public class ChitietPhieuXuatDialogFragment extends DialogFragment {


    private RecyclerView rcv_listsp_chitiet_phieuxuat;
    private List<PhieuXuat> phieuXuatList;
    private PhieuXuatAdapter phieuXuatAdapter;

    TextView delete_phieuxuat;
    IClickDeletePhieuXuat iClickDeletePhieuXuat;

    public ChitietPhieuXuatDialogFragment(List<PhieuXuat> phieuXuatList, IClickDeletePhieuXuat iClickDeletePhieuXuat) {
        this.phieuXuatList = phieuXuatList;
        this.iClickDeletePhieuXuat = iClickDeletePhieuXuat;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_chitiet_phieunhap, container,false);


        delete_phieuxuat = view.findViewById(R.id.delete_phieunhap);
        delete_phieuxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle( "Bạn có muốn xóa phieu nay?")
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }}).setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {

                        iClickDeletePhieuXuat.clickItem();

                    }
                }).show();
            }
        });


        rcv_listsp_chitiet_phieuxuat = view.findViewById(R.id.rcv_listsp_chitiet_phieunhap);
        phieuXuatAdapter = new PhieuXuatAdapter(phieuXuatList, getContext(), new IClickItemPhieuXuat() {
            @Override
            public void clickItem(PhieuXuat phieuXuat) {

            }
        }, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_listsp_chitiet_phieuxuat.setLayoutManager(linearLayoutManager1);
        rcv_listsp_chitiet_phieuxuat.setAdapter(phieuXuatAdapter);

        return  view;
    }
}

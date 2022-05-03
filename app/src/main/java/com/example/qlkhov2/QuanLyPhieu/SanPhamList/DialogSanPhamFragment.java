package com.example.qlkhov2.QuanLyPhieu.SanPhamList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlkhov2.R;

import java.util.List;

public class DialogSanPhamFragment extends AppCompatDialogFragment {


    List<DialogItemSanPham> dialogItemSanPhamList;
    IClickItemSanPhamDialog iClickItemSanPhamDialog;
    IClickThemSanPhamDialogButton iClickThemSanPhamDialogButton;

    public DialogSanPhamFragment(List<DialogItemSanPham> dialogItemSanPhamList, IClickItemSanPhamDialog iClickItemSanPhamDialog, IClickThemSanPhamDialogButton iClickThemSanPhamDialogButton) {
        this.dialogItemSanPhamList = dialogItemSanPhamList;
        this.iClickItemSanPhamDialog = iClickItemSanPhamDialog;
        this.iClickThemSanPhamDialogButton = iClickThemSanPhamDialogButton;
    }
//    public DialogSanPhamFragment(List<DialogItemSanPham> dialogItemSanPhamList, IClickItemSanPhamDialog iClickItemSanPhamDialog) {
//        this.dialogItemSanPhamList = dialogItemSanPhamList;
//        this.iClickItemSanPhamDialog = iClickItemSanPhamDialog;
//    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_sanpham, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        TextView title = new TextView(getActivity());
        title.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 0);
        title.setPadding(0,30,0,30);
        title.setLayoutParams(lp);
        title.setText("Danh Sách Sản Phẩm");
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);



        builder.setView(view).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setView(view).setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                iClickThemSanPhamDialogButton.clickItem();
            }
        });

        AlertDialog dialog  = builder.create();
        RecyclerView rcvBSD = view.findViewById(R.id.rcv_dialog_sanpham);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvBSD.setLayoutManager(linearLayoutManager);


        DialogSanPhamAdapter dialogSanPhamAdapter = new DialogSanPhamAdapter(dialogItemSanPhamList, this.iClickItemSanPhamDialog);

        rcvBSD.setAdapter(dialogSanPhamAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvBSD.addItemDecoration(itemDecoration);

        return dialog;

    }
}

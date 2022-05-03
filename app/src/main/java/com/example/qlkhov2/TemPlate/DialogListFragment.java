package com.example.qlkhov2.TemPlate;

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

public class DialogListFragment extends AppCompatDialogFragment {

    List<DialogListItem> dialogListItemList;
    IClickItemDialogList iClickItemDialogList;

    public DialogListFragment(List<DialogListItem> dialogListItemList, IClickItemDialogList iClickItemDialogList) {
        this.dialogListItemList = dialogListItemList;
        this.iClickItemDialogList = iClickItemDialogList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_list, null);
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
        title.setText("Danh Sách Kho");
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);

        builder.setView(view).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog  = builder.create();
        RecyclerView rcvBSD = view.findViewById(R.id.rcv_dialog_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvBSD.setLayoutManager(linearLayoutManager);


        DialogListAdapter dialogListAdapter = new DialogListAdapter(dialogListItemList, new IClickItemDialogList() {
            @Override
            public void clickItem(DialogListItem dialogListItem) {
                dialog.dismiss();
                iClickItemDialogList.clickItem(dialogListItem);
            }
        });
        rcvBSD.setAdapter(dialogListAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvBSD.addItemDecoration(itemDecoration);

        return dialog;
    }
}

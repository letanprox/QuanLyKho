package com.example.qlkhov2.TemPlate;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlkhov2.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheetListItemFragment extends BottomSheetDialogFragment {

    private List<DialogListItem> itemList;
    private IClickItemDialogList iClickItemDialogList;


    public BottomSheetListItemFragment(List<DialogListItem> itemList, IClickItemDialogList iClickItemDialogList) {
        this.itemList = itemList;
        this.iClickItemDialogList = iClickItemDialogList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_list, null);
        bottomSheetDialog.setContentView(view);

        RecyclerView rcvBSD = view.findViewById(R.id.rcv_dialog_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvBSD.setLayoutManager(linearLayoutManager);

        DialogListAdapter dialogListAdapter = new DialogListAdapter(itemList, new IClickItemDialogList() {
            @Override
            public void clickItem(DialogListItem dialogListItem) {
                iClickItemDialogList.clickItem(dialogListItem);
                bottomSheetDialog.hide();
            }
        });

        rcvBSD.setAdapter(dialogListAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvBSD.addItemDecoration(itemDecoration);

        return bottomSheetDialog;
    }
}

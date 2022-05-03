package com.example.qlkhov2.TemPlate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlkhov2.R;

import java.util.List;

public class DialogListAdapter extends RecyclerView.Adapter<DialogListAdapter.DialogListViewHolder>{

    private List<DialogListItem> dialogListItemList;
    private IClickItemDialogList iClickItemDialogList;

    public DialogListAdapter(List<DialogListItem> dialogListItemList, IClickItemDialogList iClickItemDialogList) {
        this.dialogListItemList = dialogListItemList;
        this.iClickItemDialogList = iClickItemDialogList;
    }

    @NonNull
    @Override
    public DialogListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_list, parent,false);
        return new DialogListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogListViewHolder holder, int position) {
        DialogListItem dialogListItem = dialogListItemList.get(position);
        if(dialogListItem == null){
            return;
        }
        holder.tv_item_dialog_list.setText(dialogListItem.getName());
        holder.tv_item_dialog_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemDialogList.clickItem(dialogListItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dialogListItemList != null){
            return dialogListItemList.size();
        }
        return 0;
    }

    public class DialogListViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_item_dialog_list;
        public DialogListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_dialog_list = itemView.findViewById(R.id.tv_item_dialog_list);
        }
    }


}

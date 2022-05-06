package com.example.qlkhov2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class Grid_Menu_Adapter extends BaseAdapter {

    Context context;
    List<Item_Menu> item_menuList;
    LayoutInflater layoutInflater;

    public Grid_Menu_Adapter(Context context, List<Item_Menu> item_menuList) {
        this.context = context;
        this.item_menuList = item_menuList;
        this.layoutInflater = LayoutInflater.from(this.context);
    }


    @Override
    public int getCount() {
        return item_menuList.size();
    }

    @Override
    public Object getItem(int i) {
        return item_menuList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.grid_menu_item, null);
            holder = new ViewHolder();

            holder.item_name = (TextView) view.findViewById(R.id.title_menu_item);
            holder.item_icon = (ImageView) view.findViewById(R.id.icon_menu_item);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Item_Menu itemdata = this.item_menuList.get(i);

        holder.item_name.setText(itemdata.getName());
        holder.item_icon.setImageResource(itemdata.getIcon());

        return view;
    }

    static class ViewHolder {
        TextView item_name;
        ImageView item_icon;
    }

}

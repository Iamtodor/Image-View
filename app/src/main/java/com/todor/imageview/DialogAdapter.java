package com.todor.imageview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DialogAdapter extends ArrayAdapter<String> {
    public DialogAdapter(Context context, ArrayList<String> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        View v = convertView;
        if(v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_adapter, parent, false);
            viewHolder.item.setText(item);
        }
        return v;
    }

    static class ViewHolder {
        TextView item;
    }
}

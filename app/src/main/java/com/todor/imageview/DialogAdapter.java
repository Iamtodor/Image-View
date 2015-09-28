package com.todor.imageview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DialogAdapter extends ArrayAdapter<String> {
    private List<String> list;

    public DialogAdapter(Context context, List<String> list) {
        super(context, 0, list);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = list.get(position);
        ViewHolder viewHolder;
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_adapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item = (TextView) v.findViewById(R.id.item);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.item.setText(item);
        return v;
    }

    static class ViewHolder {
        TextView item;
    }
}

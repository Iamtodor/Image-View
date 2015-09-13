package com.todor.imageview;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {

    public GridViewAdapter(Context context, ArrayList<ImageItem> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageItem item = (ImageItem) getItem(position);
        ImageHolder imageHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gallery_item, parent, false);
            imageHolder = new ImageHolder();
            imageHolder.imageThumb = (ImageView) convertView.findViewById(R.id.image);
            imageHolder.favorite = (ImageView) convertView.findViewById(R.id.favorite);
            convertView.setTag(imageHolder);

            imageHolder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.isSelected()) {
                        item.setSelected(false);
                        FavoriteFragment.imageItemArrayList.remove(item);
                        notifyDataSetChanged();
                    } else {
                        item.setSelected(true);
                        FavoriteFragment.imageItemArrayList.add(item);
                        notifyDataSetChanged();
                    }
                }
            });

            imageHolder.imageThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        } else {
            imageHolder = (ImageHolder) convertView.getTag();
        }

        Picasso.with(getContext())
                .load("file://" + Uri.parse(item.getImage()))
                .fit()
                .into(imageHolder.imageThumb);

        if(item.isSelected()) {
            imageHolder.favorite.setImageResource(R.drawable.starselected);
        } else {
            imageHolder.favorite.setImageResource(R.drawable.starnoselected);
        }

        return convertView;
    }

    static class ImageHolder {
        ImageView imageThumb;
        ImageView favorite;
    }
}

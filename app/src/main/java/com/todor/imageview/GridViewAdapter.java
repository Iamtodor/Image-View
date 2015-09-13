package com.todor.imageview;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.todor.imageview.model.ImageItem;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter {

    private View.OnClickListener listener;

    public GridViewAdapter(Context context, List<ImageItem> data, View.OnClickListener listener) {
        super(context, 0, data);
        this.listener = listener;
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

        imageHolder.favorite.setOnClickListener(listener);

        return convertView;
    }

    static class ImageHolder {
        ImageView imageThumb;
        ImageView favorite;
    }
}

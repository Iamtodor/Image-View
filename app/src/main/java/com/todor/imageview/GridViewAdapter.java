package com.todor.imageview;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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
        View view = convertView;
        ImageHolder holder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.gallery_item, parent, false);
            holder = new ImageHolder(view);
            view.setTag(holder);
        } else {
            holder = (ImageHolder) view.getTag();
        }

        holder.favorite.setImageResource(item.isFavorite() ? R.drawable.starselected : R.drawable.starnoselected);
        holder.favorite.setOnClickListener(listener);

        Picasso.with(getContext())
                .load("file://" + Uri.parse(item.getPath()))
                .fit()
                .centerCrop()
                .into(holder.imageThumb);

        return view;
    }

    static class ImageHolder {
        ImageView imageThumb;
        ImageView favorite;

        public ImageHolder(View view) {
            imageThumb = (ImageView) view.findViewById(R.id.image_thumb);
            favorite = (ImageView) view.findViewById(R.id.favorite);
        }
    }

}

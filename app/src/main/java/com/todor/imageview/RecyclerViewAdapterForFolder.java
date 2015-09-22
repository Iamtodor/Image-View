package com.todor.imageview;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.todor.imageview.model.GalleryImages;
import com.todor.imageview.model.ImageItem;

import java.io.Serializable;
import java.util.List;

public class RecyclerViewAdapterForFolder extends RecyclerView.Adapter<RecyclerViewAdapterForFolder.ViewHolder> implements Serializable {

    private List<ImageItem> imageItems;
    private Context context;

    public RecyclerViewAdapterForFolder(List<ImageItem> imageItems, Context context) {
        this.imageItems = imageItems;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapterForFolder.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.gallery_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterForFolder.ViewHolder viewHolder, int i) {
        final ImageItem imageItem = imageItems.get(i);
        viewHolder.favorite.setImageResource(imageItem.isFavorite() ? R.drawable.starselected : R.drawable.starnoselected);
        Picasso.with(context)
                .load("file://" + Uri.parse(imageItem.getPath()))
                .fit()
                .centerCrop()
                .into(viewHolder.imageThumb);

        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryImages.saveOrDeleteFavorite(imageItem);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageThumb;
        ImageView favorite;

        public ViewHolder(View itemView) {
            super(itemView);
            imageThumb = (ImageView) itemView.findViewById(R.id.image_thumb);
            favorite = (ImageView) itemView.findViewById(R.id.favorite);
        }
    }
}

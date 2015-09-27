package com.todor.imageview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.todor.imageview.activity.ImageActivity;
import com.todor.imageview.model.GalleryImages;

import java.io.Serializable;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Serializable {

    private List<String> imageItems;
    private Context context;

    public RecyclerViewAdapter(List<String> imageItems, Context context) {
        this.imageItems = imageItems;
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.gallery_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int i) {
//        Bitmap myBitmap = BitmapFactory.decodeFile(imageItems.get(i));
        Picasso.with(context)
                .load("file://" + Uri.parse(imageItems.get(i)))
                .fit()
                .centerCrop()
                .into(viewHolder.imageThumb);

        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                GalleryImages.saveOrDeleteFavorite(imageItem);
                notifyDataSetChanged();
            }
        });
        viewHolder.imageThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, ImageActivity.class);
//                mIntent.putExtra("path", imageItem.getPath());
                context.startActivity(mIntent);
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

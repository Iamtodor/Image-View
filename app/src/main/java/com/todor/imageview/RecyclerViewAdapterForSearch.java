package com.todor.imageview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.todor.imageview.activity.ImageActivity;
import com.todor.imageview.model.GalleryImages;
import com.todor.imageview.model.ImageItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class RecyclerViewAdapterForSearch extends RecyclerView.Adapter<RecyclerViewAdapterForSearch.ViewHolder> implements Serializable {

    private static final long serialVersionUID = -6298516694275121291L;
    private Context context;
    private ArrayList<ImageItem> imageItems;
    private Bitmap bitmap;

    public RecyclerViewAdapterForSearch(Context context) {
        this.context = context;
        this.imageItems = new ArrayList<>();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        if (bitmap != null) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
            if (success) {
                oos.writeObject(byteStream.toByteArray());
            }
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        byte[] image = (byte[]) ois.readObject();
        if (image != null && image.length > 0) {
            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
    }

    public void addResult(ImageItem imageItem) {
        imageItems.add(imageItem);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.gallery_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapterForSearch.ViewHolder viewHolder, int i) {
        final ImageItem imageItem = imageItems.get(i);
        viewHolder.favorite.setImageResource(imageItem.isFavorite() ? R.drawable.starselected : R.drawable.starnoselected);
        Picasso.with(context)
                .load(Uri.parse(imageItem.getPath()))
                .fit()
                .centerCrop()
                .into(viewHolder.imageThumb);

        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bitmap bitmap = ((BitmapDrawable) viewHolder.imageThumb.getDrawable()).getBitmap();
                GalleryImages.saveOrDeleteFavoriteFromSearch(imageItem, bitmap);
                notifyDataSetChanged();
            }
        });

        viewHolder.imageThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, ImageActivity.class);
                final Bitmap bitmap = ((BitmapDrawable) viewHolder.imageThumb.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                mIntent.putExtra("stream", stream.toByteArray());
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


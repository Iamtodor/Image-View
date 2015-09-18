package com.todor.imageview;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.todor.imageview.model.ImageItem;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ImageItem> imageItems;
    private Context context;
    private View.OnClickListener listener;
    private GetItemFromRecyclerViewListener getItemListener;

    public RecyclerViewAdapter(List<ImageItem> imageItems, Context context, View.OnClickListener listener,
                               GetItemFromRecyclerViewListener getItemListener) {
        this.imageItems = imageItems;
        this.context = context;
        this.listener = listener;
        this.getItemListener = getItemListener;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.gallery_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        ImageItem imageItem = imageItems.get(i);
        viewHolder.favorite.setImageResource(imageItem.isFavorite() ? R.drawable.starselected : R.drawable.starnoselected);
        Picasso.with(context)
                .load("file://" + Uri.parse(imageItem.getPath()))
                .fit()
                .centerCrop()
                .into(viewHolder.imageThumb);

        viewHolder.favorite.setOnClickListener(listener);
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

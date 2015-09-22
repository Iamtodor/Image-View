package com.todor.imageview;

import android.net.Uri;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.todor.imageview.fragment.SearchFragment;
import com.todor.imageview.model.GalleryImages;
import com.todor.imageview.model.ImageItem;

import java.io.Serializable;

public class RecyclerViewAdapterForSearch extends RecyclerView.Adapter<RecyclerViewAdapterForSearch.ViewHolder> implements Serializable {

    private SearchFragment fragment;
    private int contiguousResults; //Contiguous results count from front of results array
    private SimpleArrayMap<Integer, ImageItem> resultsImage;

    public RecyclerViewAdapterForSearch(SearchFragment fragment) {
        this.fragment = fragment;
        this.contiguousResults = 0;
        this.resultsImage = new SimpleArrayMap<>();
    }

    public void addResult(ImageItem imageItem) {
        this.resultsImage.put(imageItem.getResultIndex(), imageItem);
        int i = contiguousResults;
        while (resultsImage.get(i) != null) {
            i++;
        }

        if(i != contiguousResults) {
            contiguousResults = i;
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.gallery_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterForSearch.ViewHolder viewHolder, int i) {
        final ImageItem imageItem = resultsImage.get(i);
        viewHolder.favorite.setImageResource(imageItem.isFavorite() ? R.drawable.starselected : R.drawable.starnoselected);
        Picasso.with(fragment.getActivity())
                .load(Uri.parse(imageItem.getPath()))
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
        return resultsImage.size();
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

